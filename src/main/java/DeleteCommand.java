public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.deleteTask(index);
        ui.outputMessage(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list",
                    t,  taskHandler.taskCount()));
    }
}
