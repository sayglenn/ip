package ginger.command;

import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a ListCommand which outputs the list of tasks when executed.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        ui.outputMessage(String.format("Okay, here are the tasks you currently have:"
                + "\n%s", taskHandler.tasklistToString()));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ListCommand;
    }
}
