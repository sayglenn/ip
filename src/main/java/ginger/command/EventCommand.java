package ginger.command;

import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;
import java.time.LocalDateTime;

public class EventCommand extends Command {
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public EventCommand(String title, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.addEvent(this.title, this.start, this.end);
        ui.outputMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                t, taskHandler.taskCount()));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EventCommand e) {
            return e.title.equals(this.title) && e.start.equals(this.start) && e.end.equals(this.end);
        }
        return false;
    }
}
