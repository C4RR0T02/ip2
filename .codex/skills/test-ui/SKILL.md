---
name: test-ui
description: Run fail-fast console UI regression tests for this Chronos project. Use when asked to test interactive commands, verify expected terminal output, add or update UI test cases, or show a transcript of a test session.
---

# Console UI testing

Keep test cases in `test/ui-test-plan.md`. Each test case must contain an aim, an `Inputs` fenced `text` block, and an `Expected output` fenced `text` block. The expected output must be an exact match for the program's standard output; do not include terminal echo of the entered commands.

Run all cases from the repository root:

```bash
python3 test/run_ui_tests.py
```

The runner compiles all Java files under `src/main/java`, runs `Chronos` once per case, and prints a console-style input/output transcript. It stops on the first compilation error or output mismatch, reporting both expected and actual output. Update the test plan whenever the intentional UI changes, then rerun the suite.
