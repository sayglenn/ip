package ginger.command;

import ginger.exception.IllegalGingerArgumentException;
import ginger.task.Task;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a TagCommand which tags a task and outputs it when executed.
 */
public class TagCommand extends Command {

    private final int index;
    private final String tag;

    /**
     * Creates a TagCommand object which tags a specific task when executed.
     * @param index The index of the task to be tagged.
     * @param tag The tag to be given to the task.
     */
    public TagCommand(int index, String tag) {
        this.index = index;
        this.tag = tag;
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

        Task t = taskHandler.getTask(index);
        t.setTag(tag);
        taskHandler.saveTasks();
        String message = "Okay, I've added the tag to this task: " + t;
        ui.outputMessage(message);
        return message;
    }
}
