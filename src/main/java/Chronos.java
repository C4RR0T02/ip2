import java.util.Scanner;

import Task.Task;

/**
 * Starts the Chronos chatbot application.
 */
public class Chronos {

    private static final String separator = "____________________________________________________________";
    private static final String banner = "  ██████╗██╗  ██╗██████╗  ██████╗ ███╗   ██╗ ██████╗ ███████╗\n"
            + " ██╔════╝██║  ██║██╔══██╗██╔═══██╗████╗  ██║██╔═══██╗██╔════╝\n"
            + " ██║     ███████║██████╔╝██║   ██║██╔██╗ ██║██║   ██║███████╗\n"
            + " ██║     ██╔══██║██╔══██╗██║   ██║██║╚██╗██║██║   ██║╚════██║\n"
            + " ╚██████╗██║  ██║██║  ██║╚██████╔╝██║ ╚████║╚██████╔╝███████║\n"
            + "  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝ ╚══════╝";

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean exit = false;
    private static final Task[] taskList = new Task[100];
    private static int taskCount = 0;

    public static String getInput() {
        return scanner.nextLine();
    }

    public static void echo(String input) {
        System.out.println("added: " + input);
        taskList[taskCount] = new Task(input);
        taskCount++;
    }

    public static void mark(String input) {
        int taskIndex = Integer.parseInt(input) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            Task target = taskList[taskIndex];
            target.mark();
            System.out.println("Task marked as done: " + taskList[taskIndex]);
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public static void unmark(String input) {
        int taskIndex = Integer.parseInt(input) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            Task target = taskList[taskIndex];
            target.unmark();
            System.out.println("Task marked as not done: " + taskList[taskIndex]);
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public static void printList() {
        for (int i = 0; i < taskCount; i++) {
            System.out.println(i + 1 + "." + taskList[i]);
        }
    }

    public static void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Chronos.\nWhat can I do for you?");
        System.out.println(separator);

        while (!exit) {
            String input = getInput();
            switch (input.split(" ")[0]) {
                case "bye":
                    Chronos.printGoodbye();
                    exit = true;
                    break;
                case "list":
                    Chronos.printList();
                    break;
                case "mark":
                    Chronos.mark(input.split(" ")[1]);
                    break;
                case "unmark":
                    Chronos.unmark(input.split(" ")[1]);
                    break;
                default:
                    Chronos.echo(input);
                    break;
            }
            System.out.println(separator);
        }
    }
}
