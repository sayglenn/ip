public class ByeCommand extends Command {
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        taskHandler.saveTasks();
        ui.outputMessage("Bye. Hope to see you again soon!");
    }
}
