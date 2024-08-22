public class Event extends Task {
    private final String[] start;
    private final String[] end;
    public Event(String title, String[] start, String[] end) {
        super(title);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (%s: %s %s: %s", super.toString(),
                start[0].trim(), start[1].trim(), end[0].trim(), end[1].trim());
    }
}
