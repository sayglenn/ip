public class Deadline extends Task {
    private final String deadline;
    public Deadline(String title, String deadline) {
        super(title);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String[] deadlineParts = this.deadline.split(" ", 2);
        return String.format("[D]%s (%s: %s)", super.toString(), deadlineParts[0].trim(), deadlineParts[1].trim());
    }
}
