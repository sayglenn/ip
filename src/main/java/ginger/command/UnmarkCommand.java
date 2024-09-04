package ginger.command;

import ginger.exception.IllegalGingerArgumentException;
import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents an UnmarkCommand which unmarks a task and outputs it when executed.
 */
public class UnmarkCommand extends Command {

    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException {
        if (index < 0 || index > taskHandler.taskCount() - 1) {
            throw new IllegalGingerArgumentException(String.format("You entered an invalid task number! %s",
                    taskHandler.taskCount() == 0
                            ? "You have no tasks yet, do add one!"
                            : taskHandler.taskCount() == 1
                            ? "Your only task number is 1, since you only have one task."
                            : "You can enter task number from 1 - " + taskHandler.taskCount()
            ));
        }

        taskHandler.changeTaskStatus(index, false);
        Task t = taskHandler.getTask(index);
        ui.outputMessage("OK, I've marked this task as not done yet:\n  " + t);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UnmarkCommand u) {
            return u.index == this.index;
        }
        return false;
    }
}
