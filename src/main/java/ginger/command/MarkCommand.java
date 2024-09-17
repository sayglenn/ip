package ginger.command;

import ginger.exception.IllegalGingerArgumentException;
import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a MarkCommand which marks a task and outputs it when executed.
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }
    @Override
    public String execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException {
        if (index < 0 || index > taskHandler.taskCount() - 1) {
            throw new IllegalGingerArgumentException(String.format("Oh no! You entered an invalid task number! %s",
                    taskHandler.taskCount() == 0
                            ? "You have no tasks yet, do add one!"
                            : taskHandler.taskCount() == 1
                            ? "Your only task number is 1, since you only have one task."
                            : "You can enter task number from 1 - " + taskHandler.taskCount()
                        ));
        }

        taskHandler.changeTaskStatus(index, true);
        Task t = taskHandler.getTask(index);
        taskHandler.saveTasks();
        String message = "Consider it seasoned! I've marked this task as done:\n  " + t.toString();
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MarkCommand m) {
            return m.index == this.index;
        }
        return false;
    }
}
