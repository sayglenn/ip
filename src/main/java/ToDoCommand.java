public class ToDoCommand extends Command {

    private String title;

    public ToDoCommand(String title) {
        this.title = title;
    }
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.addToDo(this.title);
        ui.outputMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                t, taskHandler.taskCount()));
    }
}
