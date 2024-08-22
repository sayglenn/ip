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
            int index = Integer.parseInt(input.substring(5)) - 1;
            if (index > taskList.size() - 1 || index < 0) {
                message(String.format("There is no task numbered %d! Please try again.", index + 1));
                return;
            }
            Task t = taskList.get(index);
            t.updateStatus(true);
            message("Nice! I've marked this task as done:\n" + t);
        } else if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            if (index > taskList.size() - 1) {
                message(String.format("There is no task numbered %d! Please try again.", index + 1));
                return;
            }
            Task t = taskList.get(index);
            t.updateStatus(false);
            message("OK, I've marked this task as not done yet:\n" + t);
        } else if (input.startsWith("todo")) {
            ToDo newToDo = new ToDo(input.split(" ", 2)[1].trim());
            taskList.add(newToDo);
            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newToDo, taskList.size()));
        } else if (input.startsWith("deadline")) {
            String[] parts = input.split("/", 2);
            Deadline newDeadline = new Deadline(parts[0].split(" ", 2)[1].trim(),
                    parts[1].split(" ", 2));
            taskList.add(newDeadline);
            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newDeadline, taskList.size()));
        } else if (input.startsWith("event")) {
            String[] parts = input.split("/", 3);
            Event newEvent = new Event(parts[0].split(" ", 2)[1].trim(),
                    parts[1].split(" ", 2), parts[2].split(" ", 2));
            taskList.add(newEvent);
            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newEvent, taskList.size()));
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
