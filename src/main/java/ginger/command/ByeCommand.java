package ginger.command;

import ginger.task.TaskHandler;
import ginger.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        taskHandler.saveTasks();
        ui.outputMessage("Bye. Hope to see you again soon!");
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
