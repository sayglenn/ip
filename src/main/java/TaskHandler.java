import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskHandler {
    private List<Task> taskList;

    public TaskHandler(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int taskCount() {
        return this.taskList.size();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public void deleteTask(String input) throws IllegalGingerArgumentException {
        try {
            int index = Integer.parseInt(input) - 1;
            int taskCount = taskList.size();
            if (index < 0) {
                Ginger.message("You entered a non-positive task number! Try again!");
                return;
            }
            if (index > taskCount - 1) {
                Ginger.message(String.format("You only have %d tasks! Try again!", taskCount));
                return;
            }
            Task t = taskList.remove(index);
            Ginger.message(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list",
                    t, taskCount - 1));
        } catch (NumberFormatException e) {
            throw new IllegalGingerArgumentException("Oh no! Please enter a proper task number, e.g. delete 1");
        }
    }

    public String tasklistToString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            result.append(String.format("%d. %s", i + 1, taskList.get(i)));
            if (i != taskList.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Changes a task status to mark or unmarked
     * @param input A string containing the index of the task that is being referenced.
     * @param toMark A boolean to indicate whether to mark or unmark. True to mark, false to unmark.
     */
    public void changeTaskStatus(String input, boolean toMark) throws IllegalGingerArgumentException {
        try {
            int index = Integer.parseInt(input) - 1;
            int taskCount = taskList.size();
            if (index < 0) {
                Ginger.message("You entered a non-positive task number! Try again!");
                return;
            }
            if (index > taskCount - 1) {
                Ginger.message(String.format("You only have %d tasks! Try again!", taskCount));
                return;
            }
            Task t = taskList.get(index);
            t.updateStatus(toMark);
            Ginger.message(toMark
                    ? "Nice! I've marked this task as done:\n  " + t
                    : "OK, I've marked this task as not done yet:\n  " + t);
        } catch (NumberFormatException e) {
            throw new IllegalGingerArgumentException("Oh no! Please enter a proper task number, e.g. mark 1");
        }
    }

    public void addToDo(String input) throws IllegalGingerArgumentException {
        if (input.isEmpty()) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Please follow the format for the To Do: todo <title> " +
                            "\ne.g. todo buy food"
            );
        }

        ToDo newToDo = new ToDo(input);
        this.addTask(newToDo);
        Ginger.message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                newToDo, this.taskList.size()));
    }

    public void addDeadline(String input) throws IllegalGingerArgumentException {
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
            this.addTask(newDeadline);
            Ginger.message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newDeadline, this.taskList.size()));
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm" +
                            "\ne.g. 1/8/2023 1830 or 01/08/2023 1830 will suffice."
            );
        }
    }

    public void addEvent(String input) throws IllegalGingerArgumentException {
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
            this.addTask(newEvent);
            Ginger.message(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    newEvent, this.taskList.size()));
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm" +
                            "\ne.g. 1/8/2023 1830 or 01/08/2023 1830 will suffice."
            );
        }
    }
}
