public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException {
        if (index < 0 || index > taskHandler.taskCount() - 1) {
            throw new IllegalGingerArgumentException(String.format("You entered an invalid task number! %s",
                    taskHandler.taskCount() == 0
                            ? "You have no tasks yet, do add one!"
                            : taskHandler.taskCount() == 1
                            ? "The only task number is 1, since you only have one task."
                            : "You can enter task number from 1 - " + taskHandler.taskCount() + "."
            ));
        }

        Task t = taskHandler.deleteTask(index);
        ui.outputMessage(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                    t,  taskHandler.taskCount()));
    }
}
