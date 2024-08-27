public class Deadline extends Task {
    private final String deadline;
    public Deadline(String title, String deadline) {
        super(title);
        this.deadline = deadline;
    }

    public Deadline(String title, String deadline, boolean isCompleted) {
        super(title, isCompleted);
        this.deadline = deadline;
    }

    @Override
    public String toDbString() {
        return String.format("D %s %s", super.toDbString(), this.deadline);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline);
    }
}
