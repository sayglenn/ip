package ginger.command;

import ginger.exception.IllegalGingerArgumentException;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents an abstract class Command which contains an execute function and an isExit method.
 */
public abstract class Command {
    public abstract String execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException;

    public boolean isExit() {
        return false;
    }
}
