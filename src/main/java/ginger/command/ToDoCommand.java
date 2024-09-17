package ginger.command;

import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a ToDoCommand which creates a to do task and outputs it when executed.
 */
public class ToDoCommand extends Command {

    private final String title;

    public ToDoCommand(String title) {
        this.title = title;
    }
    @Override
    public String execute(TaskHandler taskHandler, Ui ui) {
        Task t = taskHandler.addToDo(this.title);
        taskHandler.saveTasks();
        String message = String.format("Sprinkling heat in! I've added this to do:\n  %s\n"
                        + "Now you have %d tasks in the list.",
                t, taskHandler.taskCount());
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ToDoCommand t) {
            return t.title.equals(this.title);
        }
        return false;
    }
}
