package ginger.command;

import java.time.LocalDateTime;

import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a EventCommand which creates an event task and outputs it when executed.
 */
public class EventCommand extends Command {
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates an EventCommand object which adds an event to the task list upon executed.
     * @param title The title of the event.
     * @param start The starting date and time of the event.
     * @param end The ending date and time of the event.
     */
    public EventCommand(String title, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }
    @Override
    public String execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.addEvent(this.title, this.start, this.end);
        String message = String.format("Spicing up your life! I've added this event:\n  %s\n"
                        + "Now you have %d tasks in the list.",
                t, taskHandler.taskCount());
        taskHandler.saveTasks();
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EventCommand e) {
            return e.title.equals(this.title) && e.start.equals(this.start) && e.end.equals(this.end);
        }
        return false;
    }
}
