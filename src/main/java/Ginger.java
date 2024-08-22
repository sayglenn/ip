import java.util.ArrayList;
import java.util.Scanner;

public class Ginger {
    private final static String HORIZONTAL_LINE = "____________________________________________________________";
    private final static String BOT_NAME = "Ginger";
    private final static ArrayList<Task> taskList = new ArrayList<>();

    private static void message(String message) {
        System.out.println(HORIZONTAL_LINE + "\n" + message + "\n" + HORIZONTAL_LINE);
    }

    private static String retrieveTasks() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < taskList.size(); i++) {
            result.append(String.format("%d. %s", i + 1, taskList.get(i)));
            if (i != taskList.size() - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

    private static void inputProcessor(String input) {
        if (input.startsWith("mark")) {
            try {
                int index = Integer.parseInt(input.substring(5)) - 1;
                Task t = taskList.get(index);
                t.markAsComplete();
                message("Nice! I've marked this task as done:\n" + t);
            } catch (NumberFormatException e) {
                taskList.add(new Task(input));
                message("added: " + input);
            }
        } else if (input.startsWith("unmark")) {
            try {
                int index = Integer.parseInt(input.substring(7)) - 1;
                Task t = taskList.get(index);
                t.markAsIncomplete();
                message("OK, I've marked this task as not done yet:\n" + t);
            } catch (NumberFormatException e) {
                taskList.add(new Task(input));
                message("added: " + input);
            }
        } else {
            taskList.add(new Task(input));
            message("added: " + input);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Greet the user
        message(String.format("Hello! I'm %s\nWhat can I do for you?", BOT_NAME));

        String input;
        while (true) {
            input = sc.nextLine();
            switch (input) {
                case "bye":
                    message("Bye. Hope to see you again soon!");
                    sc.close();
                    return;
                case "list":
                    message(retrieveTasks());
                    break;
                default:
                    inputProcessor(input);
                    break;
            }
        }
    }
}
