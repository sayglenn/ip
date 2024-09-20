package ginger.task;

/**
 * Encapsulates a Task which contains a title and completion status.
 * @author Say Glenn
 */
public abstract class Task {
    private final String title;
    private boolean isCompleted;

    private String tag;

    /**
     * Constructs a ginger.task.Task given title only.
     * @param title A string which is the title of the task.
     */
    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
        this.tag = "";
    }

    /**
     * Constructs a ginger.task.Task given title and completion status.
     * @param title A string which is the title of the task.
     * @param isCompleted A boolean which shows completion status of task.
     */
    public Task(String title, boolean isCompleted, String tag) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.tag = tag;
    }

    /**
     * Marks the task as complete or incomplete based on the given status.
     * @param isCompleted A boolean to indicate task completion status.
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String toDbString() {
        return String.format("| %s | %s | %s |", this.isCompleted ? "1" : "0", this.title, this.tag);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return isCompleted
                ? String.format("[X] %s %s", this.title, this.tag.isEmpty() ? "" : "[" + this.tag + "]")
                : String.format("[ ] %s %s", this.title, this.tag.isEmpty() ? "" : "[" + this.tag + "]");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Task t) {
            return t.title.equals(this.title) && this.isCompleted == t.isCompleted;
        }
        return false;
    }
}
