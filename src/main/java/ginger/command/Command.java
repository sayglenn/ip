package ginger.command;

import ginger.exception.IllegalGingerArgumentException;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException;

    public boolean isExit() {
        return false;
    }
}
