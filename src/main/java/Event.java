public class Event extends Task {
    private final String start;
    private final String end;
    public Event(String title, String start, String end) {
        super(title);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        String[] startParts = this.start.split(" ", 2);
        String[] endParts = this.end.split(" ", 2);
        return String.format("[E]%s (%s: %s %s: %s)", super.toString(),
                startParts[0].trim(), startParts[1].trim(), endParts[0].trim(), endParts[1].trim());
    }
}
