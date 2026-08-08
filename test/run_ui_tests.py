#!/usr/bin/env python3
"""Run the console UI test cases defined in test/ui-test-plan.md."""

from __future__ import annotations

import argparse
import re
import subprocess
import sys
import tempfile
from dataclasses import dataclass
from pathlib import Path


@dataclass
class TestCase:
    """One UI test case with its purpose, commands, and expected output."""

    name: str
    aim: str
    commands: str
    expected_output: str


CASE_PATTERN = re.compile(
    r"^## Test Case: (?P<name>.+?)\n+"
    r"\*\*Aim:\*\* (?P<aim>.+?)\n+\n"
    r"\*\*Inputs:\*\*\n```text\n(?P<inputs>.*?)\n```\n\n"
    r"\*\*Expected output:\*\*\n```text\n(?P<expected>.*?)\n```",
    re.MULTILINE | re.DOTALL,
)


def parse_test_plan(plan_path: Path) -> list[TestCase]:
    """Read test cases from the project's Markdown test plan."""
    plan = plan_path.read_text(encoding="utf-8")
    cases = [
        TestCase(
            match.group("name"),
            match.group("aim"),
            match.group("inputs"),
            match.group("expected") + "\n",
        )
        for match in CASE_PATTERN.finditer(plan)
    ]
    if not cases:
        raise ValueError(
            "No test cases found. Use the Test Case, Aim, Inputs, and Expected output format "
            "in the test plan."
        )
    return cases


def compile_program(project_dir: Path, classes_dir: Path) -> None:
    """Compile all project Java sources into a temporary directory."""
    source_dir = project_dir / "src/main/java"
    sources = sorted(source_dir.rglob("*.java"))
    if not sources:
        raise ValueError(f"No Java source files found under {source_dir}")

    result = subprocess.run(
        ["javac", "-d", str(classes_dir), *map(str, sources)],
        capture_output=True,
        text=True,
        check=False,
    )
    if result.returncode != 0:
        sys.stderr.write("Compilation failed:\n" + result.stderr)
        raise RuntimeError("Compilation failed")


def show_transcript(case: TestCase, actual_output: str) -> None:
    """Print the commands and output from one console test session."""
    print(f"\n=== {case.name} ===")
    print(f"Aim: {case.aim}")
    print("Console input:")
    for command in case.commands.splitlines():
        print(f"> {command}")
    print("Console output:")
    print(actual_output, end="" if actual_output.endswith("\n") else "\n")


def run_case(case: TestCase, classes_dir: Path) -> str:
    """Run Chronos once with a test case's commands and return standard output."""
    result = subprocess.run(
        ["java", "-cp", str(classes_dir), "Chronos"],
        input=case.commands + "\n",
        capture_output=True,
        text=True,
        check=False,
    )
    if result.returncode != 0:
        sys.stderr.write(result.stderr)
        raise RuntimeError(f"{case.name} terminated with exit code {result.returncode}")
    return result.stdout


def main() -> int:
    """Compile Chronos and run every documented UI test case."""
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--project-dir", type=Path, default=Path.cwd())
    parser.add_argument("--plan", type=Path, default=Path("test/ui-test-plan.md"))
    args = parser.parse_args()

    project_dir = args.project_dir.resolve()
    plan_path = args.plan if args.plan.is_absolute() else project_dir / args.plan
    try:
        cases = parse_test_plan(plan_path)
    except (OSError, ValueError) as error:
        print(f"Unable to read test plan: {error}", file=sys.stderr)
        return 2

    with tempfile.TemporaryDirectory(prefix="chronos-ui-tests-") as temporary_dir:
        classes_dir = Path(temporary_dir)
        try:
            compile_program(project_dir, classes_dir)
        except (OSError, RuntimeError, ValueError):
            return 2

        for case in cases:
            try:
                actual_output = run_case(case, classes_dir)
            except RuntimeError as error:
                print(f"\nFAIL: {error}", file=sys.stderr)
                return 1

            show_transcript(case, actual_output)
            if actual_output != case.expected_output:
                print("FAIL: Output mismatch.")
                print("Expected output:")
                print(case.expected_output, end="")
                print("Actual output:")
                print(actual_output, end="")
                return 1

    print("\nPASS: All UI test cases passed.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
