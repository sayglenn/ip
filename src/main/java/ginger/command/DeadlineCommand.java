package ginger.command;

import java.time.LocalDateTime;

import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a DeadlineCommand which creates a deadline task and outputs it when executed.
 */
public class DeadlineCommand extends Command {
    private final String title;
    private final LocalDateTime deadline;

    /**
     * Creates a DeadlineCommand object with a title and deadline to be executed which adds a deadline.
     * @param title The title of the deadline.
     * @param deadline The date and time of the deadline.
     */
    public DeadlineCommand(String title, LocalDateTime deadline) {
        this.title = title;
        this.deadline = deadline;
    }
    @Override
    public String execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.addDeadline(this.title, this.deadline);
        String message = String.format("Spicy choice! I've added this deadline:\n  %s\n"
                        + "Now you have %d tasks in the list.",
                t, taskHandler.taskCount());
        taskHandler.saveTasks();
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DeadlineCommand d) {
            return d.title.equals(this.title) && d.deadline.equals(this.deadline);
        }
        return false;
    }
}
