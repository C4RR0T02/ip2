import java.time.DateTimeException;
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
            "* Date format should be dd-MM-yyyy or dd-MM-yyyy HH:mm:ss";

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

    public static void addToTasklist(Task task) throws ChronosException {
        if (taskCount == taskList.length) {
            throw new ChronosException("Task list is full. Remove a task before adding another one.");
        }
        System.out.println("added: " + task);
        taskList[taskCount] = task;
        taskCount++;
    }

    public static void mark(String input) throws ChronosException {
        Task target = getTask(input);
        target.mark();
        System.out.println("Task marked as done: " + target);
    }

    public static void unmark(String input) throws ChronosException {
        Task target = getTask(input);
        target.unmark();
        System.out.println("Task marked as not done: " + target);
    }

    public static void addTodo(String input) throws ChronosException {
        addToTasklist(new ToDo(input));
    }

    public static void addDeadline(String input) throws ChronosException {
        String[] details = input.split(",", 2);
        if (details.length != 2 || details[0].isBlank() || details[1].isBlank()) {
            throw new ChronosException("Use deadline <task>, <deadline>.");
        }
        try {
            Deadline newDeadline = new Deadline(details[0].trim(), details[1].trim());
            addToTasklist(newDeadline);
        } catch (DateTimeException exception) {
            throw new ChronosException("Deadline date must use dd-MM-yyyy or dd-MM-yyyy HH:mm:ss.");
        }
    }

    public static void addEvent(String input) throws ChronosException {
        String[] details = input.split(",", 3);
        if (details.length != 3 || details[0].isBlank() || details[1].isBlank() || details[2].isBlank()) {
            throw new ChronosException("Use event <name>, <start>, <end>.");
        }
        try {
            Event newEvent = new Event(details[0].trim(), details[1].trim(), details[2].trim());
            if (!newEvent.getStart().isBefore(newEvent.getEnd())) {
                throw new ChronosException("Event start date must be before its end date.");
            }
            addToTasklist(newEvent);
        } catch (DateTimeException exception) {
            throw new ChronosException("Event dates must use dd-MM-yyyy or dd-MM-yyyy HH:mm:ss.");
        }
    }

    public static void printList() {
        for (int i = 0; i < taskCount; i++) {
            System.out.println(i + 1 + ". " + taskList[i]);
        }
    }

    public static void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static String getTaskArgument(String command, String argument) throws ChronosException {
        if (argument.isBlank()) {
            throw new ChronosException("The " + command + " command needs an argument.");
        }
        return argument;
    }

    private static Task getTask(String input) throws ChronosException {
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(input) - 1;
        } catch (NumberFormatException exception) {
            throw new ChronosException("Task number must be a whole number.");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new ChronosException("Task number must refer to an existing task.");
        }
        return taskList[taskIndex];
    }

    public static void main(String[] args) {
        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Chronos.\nWhat can I do for you?");
        System.out.println(separator);

        while (!exit) {
            try {
                String input = getInput();
                if (input.isBlank()) {
                    throw new ChronosException("Please enter a command.");
                }
                String command = input.split(" ")[0];
                String commandTrim = input.substring(command.length()).trim();

                switch (command) {
                case "bye":
                    Chronos.printGoodbye();
                    exit = true;
                    break;
                case "list":
                    Chronos.printList();
                    break;
                case "mark":
                    Chronos.mark(getTaskArgument(command, commandTrim));
                    break;
                case "unmark":
                    Chronos.unmark(getTaskArgument(command, commandTrim));
                    break;
                case "todo":
                    Chronos.addTodo(getTaskArgument(command, commandTrim));
                    break;
                case "event":
                    Chronos.addEvent(getTaskArgument(command, commandTrim));
                    break;
                case "deadline":
                    Chronos.addDeadline(getTaskArgument(command, commandTrim));
                    break;
                case "help":
                    Chronos.getHelp();
                    break;
                default:
                    throw new ChronosException("Unknown command: " + command + ". Type help to see available commands.");
                }
            } catch (ChronosException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
            System.out.println(separator);
        }
    }
}
