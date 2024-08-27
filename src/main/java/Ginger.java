import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
            "help - Displays this set of information again.\n" +
            "delete - Deletes a task from the list. Usage: delete <task number>\n\n" +
            "Do note that Ginger supports date and time formatting. Please follow the convention dd/mm/yyyy HHmm\n" +
            "when entering your time! Examples: 01/08/2024 1830, 1/8/2024 1830 is also accepted.";
    private final static TaskStorage taskStorage = new TaskStorage("tasks.txt");
    private final static TaskHandler taskHandler = new TaskHandler(taskStorage.readTasks());

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

    /**
     * Displays a message from Ginger.
     * @param message The message to be displayed to the user.
     */
    public static void message(String message) {
        System.out.println(HORIZONTAL_LINE + "\n" + message + "\n" + HORIZONTAL_LINE);
    }

    private static void addToDo(String input) throws IllegalGingerArgumentException {
        if (input.isEmpty()) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the To Do: todo <title> " +
                            "\ne.g. todo buy food"
            );
        }

        ToDo newToDo = new ToDo(input);
        taskHandler.addTask(newToDo);
        message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newToDo, taskHandler.taskCount()));
    }

    private static void addDeadline(String input) throws IllegalGingerArgumentException {
        String[] deadlineParts = input.split("/by", 2);
        if (deadlineParts.length < 2) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the Deadline: deadline <title> /by <time> " +
                            "\ne.g. deadline submit work /by 1/8/2023 2000"
            );
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime deadline = LocalDateTime.parse(deadlineParts[1].trim(), formatter);
            Deadline newDeadline = new Deadline(deadlineParts[0].trim(), deadline);
            taskHandler.addTask(newDeadline);
            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newDeadline, taskHandler.taskCount()));
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm" +
                            "\ne.g. 1/8/2023 1830 or 01/08/2023 1830 will suffice."
            );
        }
    }

    private static void addEvent(String input) throws IllegalGingerArgumentException {
        String[] eventParts = input.split("/from", 2);
        if (eventParts.length < 2) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the Event: event <title> /from <time> /to <time>" +
                            "\ne.g. event dinner /from 01/08/2023 6pm /to 01/08/2023 10pm"
            );
        }
        String[] timeParts = eventParts[1].split("/to", 2);
        if (timeParts.length < 2) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the Event: event <title> /from <time> /to <time>" +
                            "\ne.g. event dinner /from 01/08/2023 6pm /to 01/08/2023 10pm"
            );
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime start = LocalDateTime.parse(timeParts[0].trim(), formatter);
            LocalDateTime end = LocalDateTime.parse(timeParts[1].trim(), formatter);
            Event newEvent = new Event(eventParts[0].trim(), start, end);
            taskHandler.addTask(newEvent);
            message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newEvent, taskHandler.taskCount()));
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm" +
                            "\ne.g. 1/8/2023 1830 or 01/08/2023 1830 will suffice."
            );
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Greet the user
        message(String.format("Hello! I'm %s\nHow can I spice things up for you?", BOT_NAME));

        String input;
        while (true) {
            input = sc.nextLine();
            String[] inputParts = input.split(" ", 2);
            try {
                Command command = Command.getCommand(inputParts[0].trim());
                switch (command) {
                    case BYE:
                        message("Bye. Hope to see you again soon!");
                        taskStorage.saveTasks(taskHandler.getTaskList());
                        sc.close();
                        return;
                    case LIST:
                        message(taskHandler.tasklistToString());
                        break;
                    case MARK:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. mark <arguments>");
                            break;
                        }
                        taskHandler.changeTaskStatus(inputParts[1].trim(), true);
                        break;
                    case UNMARK:
                        if (inputParts.length < 2) {
                            message("Oh no! There are insufficient arguments\n" +
                                    "e.g. unmark <arguments>");
                            break;
                        }
                        taskHandler.changeTaskStatus(inputParts[1].trim(), false);
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
                        taskHandler.deleteTask(inputParts[1].trim());
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
