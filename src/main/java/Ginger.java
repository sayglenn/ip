import java.util.ArrayList;
import java.util.Scanner;

public class Ginger {
    private final static String HORIZONTAL_LINE = "____________________________________________________________";
    private final static String BOT_NAME = "Ginger";
    private final static ArrayList<Task> taskList = new ArrayList<>();

    private static String retrieveTasks() {
        String result = "";

        for (int i = 0; i < taskList.size(); i++) {
            result += (i + 1) + ". " + taskList.get(i) + "\n";
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Greet the user
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        String input;
        while (true) {
            input = sc.nextLine();
            switch (input) {
                case "bye":
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(HORIZONTAL_LINE);
                    sc.close();
                    return;
                case "list":
                    System.out.println(HORIZONTAL_LINE);
                    System.out.print(retrieveTasks());
                    System.out.println(HORIZONTAL_LINE);
                    break;
                default:
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("added: " + input);
                    System.out.println(HORIZONTAL_LINE);
                    taskList.add(new Task(input));
                    break;
            }
        }
    }
}
