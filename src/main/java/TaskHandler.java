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
}
