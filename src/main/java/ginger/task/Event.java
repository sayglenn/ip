package ginger.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Encapsulates an event which contains a title of the event, completion status of the event and the start and
 * end times of the event.
 */
public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates an event with a title, starting time, ending time and completion status defaulted to false.
     * @param title The title of the event.
     * @param start The starting time of the event.
     * @param end The ending time of the event.
     */
    public Event(String title, LocalDateTime start, LocalDateTime end) {
        super(title);
        this.start = start;
        this.end = end;
    }

    /**
     * Creates an event with a title, starting time, ending time and completion status. Used for loading local tasks.
     * @param title The title of the event.
     * @param start The starting time of the event.
     * @param end The ending time of the event.
     * @param isCompleted The completion status of the event.
     * @param tag The tag of the event.
     */
    public Event(String title, LocalDateTime start, LocalDateTime end, boolean isCompleted, String tag) {
        super(title, isCompleted, tag);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toDbString() {
        return String.format("E %s %s | %s", super.toDbString(), this.start, this.end);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return String.format("[E]%s (%s - %s)", super.toString(),
                this.start.format(formatter), this.end.format(formatter));
    }
}
