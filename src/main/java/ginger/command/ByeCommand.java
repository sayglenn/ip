package ginger.command;

import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a ByeCommand which saves tasks and outputs a goodbye message upon being executed.
 */
public class ByeCommand extends Command {
    @Override
    public String execute(TaskHandler taskHandler, Ui ui) {
        taskHandler.saveTasks();
        String message = "Bye. Hope to sprinkle more help in your life!";
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ByeCommand;
    }
}
