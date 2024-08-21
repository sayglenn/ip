public class Task {
    private final String title;
    private boolean isCompleted;

    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    /**
     * Marks the task as complete.
     */
    public void markAsComplete() {
        this.isCompleted = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markAsIncomplete() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        return isCompleted
                ? "[X] " + this.title
                : "[ ]" + this.title;
    }
}
