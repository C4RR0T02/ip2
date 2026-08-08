import java.util.Scanner;

import Task.Deadline;
import Task.Event;
import Task.Task;
import Task.ToDo;

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
    private static final String helpMessage =
            "Here are the commands you can use:\n" +
            "list - Display all tasks\n" +
            "mark <index> - Mark a task as done\n" +
            "unmark <index> - Mark a task as not done\n" +
            "todo <task> - Add a new todo task\n" +
            "event <name>, <start*>, <end*> - Add a new event\n" +
            "deadline <task>, <deadline*> - Add a new deadline\n" +
            "help - Display this help message\n" +
            "bye - Exit the application\n" +
            "* Date format should be dd-MM-yyyy HH:mm:ss if time is omitted, it will default to 00:00:00";

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean exit = false;
    private static final Task[] taskList = new Task[100];
    private static int taskCount = 0;

    public static String getInput() {
        return scanner.nextLine();
    }

    public static void getHelp() {
        System.out.print(helpMessage);
    }

    public static void addToTasklist(Task task) {
        System.out.println("added: " + task);
        taskList[taskCount] = task;
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

    public static void addTodo(String input) {
        ToDo newTodo = new ToDo(input);
        addToTasklist(newTodo);
    }

    public static void addDeadline(String input) {
        String taskName = input.split(",")[0].trim();
        String deadline = input.split(",")[1].trim();
        Deadline newDeadline = new Deadline(taskName, deadline);
        addToTasklist(newDeadline);
    }

    public static void addEvent(String input) {
        String eventName = input.split(",")[0].trim();
        String start = input.split(",")[1].trim();
        String end = input.split(",")[2].trim();
        Event newEvent = new Event(eventName, start, end);
        addToTasklist(newEvent);
    }

    public static void printList() {
        for (int i = 0; i < taskCount; i++) {
            System.out.println(i + 1 + ". " + taskList[i]);
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
                case "todo":
                    Chronos.addTodo(input.substring(5));
                    break;
                case "event":
                    Chronos.addEvent(input.substring(6));
                    break;
                case "deadline":
                    Chronos.addDeadline(input.substring(9));
                    break;
                default:
                    Chronos.getHelp();
                    break;
            }
            System.out.println(separator);
        }
    }
}
