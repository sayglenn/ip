public class Task {
    private final String title;
    private boolean isCompleted;

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
