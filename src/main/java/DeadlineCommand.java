import java.time.LocalDateTime;

public class DeadlineCommand extends Command {
    private String title;
    private LocalDateTime deadline;

    public DeadlineCommand(String title, LocalDateTime deadline) {
        this.title = title;
        this.deadline = deadline;
    }
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.addDeadline(this.title, this.deadline);
        ui.outputMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                t, taskHandler.taskCount()));
    }
}
