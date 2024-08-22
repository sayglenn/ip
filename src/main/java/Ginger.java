import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Ginger {
    private final static String HORIZONTAL_LINE = "____________________________________________________________";
    private final static String BOT_NAME = "Ginger";
    private final static ArrayList<Task> taskList = new ArrayList<>();

    enum Command {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, HELP, UNKNOWN;

        public static Command getCommand(String command) {
            try {
                return Command.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Command.UNKNOWN;
            }
        }
    }

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

    /**
     * Changes a task status to mark or unmarked
     * @param input A string containing the index of the task that is being referenced.
     * @param toMark A boolean to indicate whether to mark or unmark. True to mark, false to unmark.
     */
    private static void changeTaskStatus(String input, boolean toMark) {
        if (input.isEmpty()) {
            message("You didn't provide the task number! Please try again. For example, mark 1.");
        } else {
            try {
                int index = Integer.parseInt(input) - 1;
                int taskCount = taskList.size();
                if (index < 0) {
                    message("You entered a non-positive task number! Try again!");
                }

                if (index > taskCount - 1) {
                    message(String.format("You only have %d tasks! Try again!", taskCount));
                    return;
                }
                Task t = taskList.get(index);
                t.updateStatus(toMark);
                message(toMark
                        ? "Nice! I've marked this task as done:\n  " + t
                        : "OK, I've marked this task as not done yet:\n  " + t);
            } catch (NumberFormatException e) {
                message("Please enter a proper number for marking / unmarking tasks!");
            }
        }
    }

    private static void addToDo(String input) {
        ToDo newToDo = new ToDo(input);
        taskList.add(newToDo);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newToDo, taskList.size()));
    }

    private static void addDeadline(String input) {
        String[] deadlineParts = input.split("/", 2);
        if (deadlineParts.length < 2) {
            message("There are too little arguments for an deadline!");
            return;
        }
        Deadline newDeadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
        taskList.add(newDeadline);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newDeadline, taskList.size()));
    }

    private static void addEvent(String input) {
        String[] eventParts = input.split("/", 3);
        if (eventParts.length < 3) {
            message("There are too little arguments for an event!");
            return;
        }
        Event newEvent = new Event(eventParts[0].trim(),
                    eventParts[1].trim(), eventParts[2].trim());
        taskList.add(newEvent);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newEvent, taskList.size()));
    }

//    private static void inputProcessor(String input) {
//        if (input.startsWith("mark")) {
//            int index = Integer.parseInt(input.substring(5)) - 1;
//            if (index > taskList.size() - 1 || index < 0) {
//                message(String.format("There is no task numbered %d! Please try again.", index + 1));
//                return;
//            }
//            Task t = taskList.get(index);
//            t.updateStatus(true);
//            message("Nice! I've marked this task as done:\n" + t);
//        } else if (input.startsWith("unmark")) {
//            int index = Integer.parseInt(input.substring(7)) - 1;
//            if (index > taskList.size() - 1) {
//                message(String.format("There is no task numbered %d! Please try again.", index + 1));
//                return;
//            }
//            Task t = taskList.get(index);
//            t.updateStatus(false);
//            message("OK, I've marked this task as not done yet:\n" + t);
//        } else if (input.startsWith("todo")) {
//            ToDo newToDo = new ToDo(input.split(" ", 2)[1].trim());
//            taskList.add(newToDo);
//            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
//                    newToDo, taskList.size()));
//        } else if (input.startsWith("deadline")) {
//            String[] parts = input.split("/", 2);
//            Deadline newDeadline = new Deadline(parts[0].split(" ", 2)[1].trim(),
//                    parts[1].split(" ", 2));
//            taskList.add(newDeadline);
//            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
//                    newDeadline, taskList.size()));
//        } else if (input.startsWith("event")) {
//            String[] parts = input.split("/", 3);
//            Event newEvent = new Event(parts[0].split(" ", 2)[1].trim(),
//                    parts[1].split(" ", 2), parts[2].split(" ", 2));
//            taskList.add(newEvent);
//            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
//                    newEvent, taskList.size()));
//        }
//    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Greet the user
        message(String.format("Hello! I'm %s\nHow can I spice things up for you?", BOT_NAME));

        String input;
        while (true) {
            input = sc.nextLine();
            String[] inputParts = input.split(" ", 2);
            Command command = Command.getCommand(inputParts[0].trim());
            switch (command) {
                case BYE:
                    message("Bye. Hope to see you again soon!");
                    sc.close();
                    return;
                case LIST:
                    message(retrieveTasks());
                    break;
                case MARK:
                    changeTaskStatus(inputParts[1].trim(), true);
                    break;
                case UNMARK:
                    changeTaskStatus(inputParts[1].trim(), false);
                    break;
                case TODO:
                    addToDo(inputParts[1].trim());
                    break;
                case DEADLINE:
                    addDeadline(inputParts[1].trim());
                    break;
                case EVENT:
                    addEvent(inputParts[1].trim());
                    break;
                default:
                    message("I don't understand this command. Try again, or type help to show all commands!");
                    break;
            }
        }
    }
}
