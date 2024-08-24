/**
 * This abstract class encapsulates a Task which contains a title and completion.
 * @author Say Glenn
 */
public abstract class Task {
    private final String title;
    private boolean isCompleted;

    /**
     * A constructor for a Task.
     * @param title A string which is the title of the task.
     */
    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    /**
     * Marks the task as complete or incomplete based on the given status.
     * @param status A boolean to indicate task completion status.
     */
    public void updateStatus(boolean status) {
        this.isCompleted = status;
    }

    @Override
    public String toString() {
        return isCompleted
                ? "[X] " + this.title
                : "[ ] " + this.title;
    }
}
