public class ListCommand extends Command {

    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        ui.outputMessage(String.format("Okay, here are the tasks you currently have:" +
                "\n%s", taskHandler.tasklistToString()));
    }
}
