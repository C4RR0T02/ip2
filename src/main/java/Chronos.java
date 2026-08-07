import java.util.Scanner;

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
    private static final String[] taskList = new String[100];
    private static int taskCount = 0;

    public static String getInput() {
        return scanner.nextLine();
    }

    public static void echo(String input) {
        System.out.println("added: " + input);
        taskList[taskCount] = input;
        taskCount++;
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
            switch (input) {
                case "bye":
                    Chronos.printGoodbye();
                    exit = true;
                    break;
                case "list":
                    Chronos.printList();
                    break;
                default:
                    Chronos.echo(input);
            }
            System.out.println(separator);
        }
    }
}
