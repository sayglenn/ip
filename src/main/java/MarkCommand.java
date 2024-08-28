public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        taskHandler.changeTaskStatus(index, true);
        Task t = taskHandler.getTask(index);
        ui.outputMessage("Nice! I've marked this task as done:\n  " + t);
    }
}
