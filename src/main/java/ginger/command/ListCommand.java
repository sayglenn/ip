package ginger.command;

import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a ListCommand which outputs the list of tasks when executed.
 */
public class ListCommand extends Command {

    @Override
    public String execute(TaskHandler taskHandler, Ui ui) {
        String message = String.format("Okay, here are the tasks you currently have:"
                + "\n%s", taskHandler.taskListToString());
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ListCommand;
    }
}
