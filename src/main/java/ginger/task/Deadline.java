package ginger.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Encapsulates a deadline which will contain the title of the deadline, the completion status and the time the
 * deadline needs to be completed by.
 */
public class Deadline extends Task {
    private final LocalDateTime deadline;
    public Deadline(String title, LocalDateTime deadline) {
        super(title);
        this.deadline = deadline;
    }

    public Deadline(String title, LocalDateTime deadline, boolean isCompleted) {
        super(title, isCompleted);
        this.deadline = deadline;
    }

    @Override
    public String toDbString() {
        return String.format("D %s %s", super.toDbString(), this.deadline);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return String.format("[D]%s (due: %s)", super.toString(), this.deadline.format(formatter));
    }
}
