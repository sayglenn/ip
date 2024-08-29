package ginger.task;

public class ToDo extends Task {
    public ToDo(String title) {
        super(title);
    }

    public ToDo(String title, boolean isCompleted) {
        super(title, isCompleted);
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
