import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ginger {
    private final static String HORIZONTAL_LINE = "____________________________________________________________";
    private final static String BOT_NAME = "Ginger";
    private final static String helpMessage = "Here is a list of commands to get you started:\n" +
            "bye - Exits the Ginger chatbot.\n" +
            "list - Lists the current tasks.\n" +
            "mark - Marks a task as complete. Usage: mark <task number>\n" +
            "unmark - Marks a task as incomplete. Usage: unmark <task number>\n" +
            "todo - Adds a To Do task. Usage: todo <title>\n" +
            "deadline - Adds a Deadline task which includes a deadline. Usage: deadline <title> /by <time>\n" +
            "event - Adds an Event task which has start and end time. Usage: event <title> /from <time> /to <time>\n" +
            "help - Displays this set of information again.";
    private final static ArrayList<Task> taskList = new ArrayList<>();

    enum Command {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, HELP, DELETE;

        public static Command getCommand(String command) throws IllegalGingerCommandException {
            try {
                return Command.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalGingerCommandException(
                        "Oh no! This command does not exist. Try again or enter help for a list of commands.");
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
    private static void changeTaskStatus(String input, boolean toMark) throws IllegalGingerArgumentException {
        try {
            int index = Integer.parseInt(input) - 1;
            int taskCount = taskList.size();
            if (index < 0) {
                message("You entered a non-positive task number! Try again!");
                return;
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
            throw new IllegalGingerArgumentException("Oh no! Please enter a proper task number, e.g. mark 1");
        }
    }

    private static void addToDo(String input) throws IllegalGingerArgumentException {
        if (input.isEmpty()) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the To Do: todo <title>" +
                    "\ne.g. todo buy food"
            );
        }

        ToDo newToDo = new ToDo(input);
        taskList.add(newToDo);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newToDo, taskList.size()));
    }

    private static void addDeadline(String input) throws IllegalGingerArgumentException {
        String[] deadlineParts = input.split("/by", 2);
        if (deadlineParts.length < 2) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the Deadline: deadline <title> /by <time>" +
                    "\ne.g. deadline submit work /by 8pm"
            );
        }
        Deadline newDeadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
        taskList.add(newDeadline);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newDeadline, taskList.size()));
    }

    private static void addEvent(String input) throws IllegalGingerArgumentException {
        String[] eventParts = input.split("/from", 2);
        if (eventParts.length < 2) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the Event: event <title> /from <time> /to <time>" +
                            "\ne.g. event dinner /from 6pm /to 10pm"
            );
        }
        String[] timeParts = eventParts[1].split("/to", 2);
        if (timeParts.length < 2) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the Event: event <title> /from <time> /to <time>" +
                            "\ne.g. event dinner /from 6pm /to 10pm"
            );
        }
        Event newEvent = new Event(eventParts[0].trim(),
                    timeParts[0].trim(), timeParts[1].trim());
        taskList.add(newEvent);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newEvent, taskList.size()));
    }

    private static void deleteTask(String input) throws IllegalGingerArgumentException {
        try {
            int index = Integer.parseInt(input) - 1;
            int taskCount = taskList.size();
            if (index < 0) {
                message("You entered a non-positive task number! Try again!");
                return;
            }
            if (index > taskCount - 1) {
                message(String.format("You only have %d tasks! Try again!", taskCount));
                return;
            }
            Task t = taskList.remove(index);
            message(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list",
                    t, taskCount - 1));
        } catch (NumberFormatException e) {
            throw new IllegalGingerArgumentException("Oh no! Please enter a proper task number, e.g. delete 1");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Greet the user
        message(String.format("Hello! I'm %s\nHow can I spice things up for you?", BOT_NAME));

        File taskFile = new File("tasks.txt");

        if (!taskFile.exists()) {
            try {
                message("No database found! Ginger will create one now.");
                taskFile.createNewFile();
                message("Database created!");
            } catch (IOException e) {
                message(e.getMessage());
            }
        }


        try {
            Scanner dbScanner = new Scanner(taskFile);
            while (dbScanner.hasNextLine()) {
                String line = dbScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts[0].trim().equals("T")) {
                    taskList.add(new ToDo(parts[2].trim(), parts[1].trim().equals("1")));
                } else if (parts[0].trim().equals("D")) {
                    taskList.add(new Deadline(parts[2].trim(), parts[3].trim(), parts[1].trim().equals("1")));
                } else {
                    taskList.add(new Event(
                            parts[2].trim(), parts[3].trim(), parts[4].trim(), parts[1].trim().equals("1")
                    ));
                }
            }
            dbScanner.close();
        } catch (FileNotFoundException e) {
            message("File not found: " + e.getMessage());
        }

        String input;
        while (true) {
            input = sc.nextLine();
            String[] inputParts = input.split(" ", 2);
            try {
                Command command = Command.getCommand(inputParts[0].trim());
                switch (command) {
                    case BYE:
                        message("Bye. Hope to see you again soon!");
                        try {
                            FileWriter fw = new FileWriter("tasks.txt");
                            BufferedWriter bw = new BufferedWriter(fw);
                            taskList.forEach(task -> {
                                try {
                                    bw.write(task.toDbString());
                                    bw.newLine();
                                } catch (IOException e) {
                                    message(e.getMessage());
                                }
                            });
                            bw.close();
                        } catch (IOException e) {
                            message(e.getMessage());
                        }
                        sc.close();
                        return;
                    case LIST:
                        message(retrieveTasks());
                        break;
                    case MARK:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. mark <arguments>");
                            break;
                        }
                        changeTaskStatus(inputParts[1].trim(), true);
                        break;
                    case UNMARK:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. unmark <arguments>");
                            break;
                        }
                        changeTaskStatus(inputParts[1].trim(), false);
                        break;
                    case TODO:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. todo <arguments>");
                            break;
                        }
                        addToDo(inputParts[1].trim());
                        break;
                    case DEADLINE:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. deadline <arguments>");
                            break;
                        }
                        addDeadline(inputParts[1].trim());
                        break;
                    case EVENT:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. event <arguments>");
                            break;
                        }
                        addEvent(inputParts[1].trim());
                        break;
                    case DELETE:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. delete <arguments>");
                            break;
                        }
                        deleteTask(inputParts[1].trim());
                        break;
                    case HELP:
                        message(helpMessage);
                        break;
                }
            } catch (IllegalGingerCommandException | IllegalGingerArgumentException e) {
                message(e.getMessage());
            }
        }
    }
}
