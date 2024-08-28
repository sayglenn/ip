public class UnmarkCommand extends Command {

    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        taskHandler.changeTaskStatus(index, false);
        Task t = taskHandler.getTask(index);
        ui.outputMessage("OK, I've marked this task as not done yet:\n  " + t);
    }
}
