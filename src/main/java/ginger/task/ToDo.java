package ginger.task;

/**
 * Encapsulates a to do which contains a title and completion status.
 */
public class ToDo extends Task {
    public ToDo(String title) {
        super(title);
    }

    public ToDo(String title, boolean isCompleted, String tag) {
        super(title, isCompleted, tag);
    }

    @Override
    public String toDbString() {
        return String.format("T %s", super.toDbString());
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
