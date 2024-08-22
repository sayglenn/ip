public class Deadline extends Task {
    private final String[] deadline;
    public Deadline(String title, String[] deadline) {
        super(title);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (%s: %s)", super.toString(), deadline[0], deadline[1]);
    }
}
