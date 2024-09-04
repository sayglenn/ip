package ginger.command;

import ginger.exception.IllegalGingerArgumentException;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a FindCommand which contains the input text and prints the tasks with matching names.
 */
public class FindCommand extends Command {

    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException {
        ui.outputMessage("Searching...\nHere are the matching tasks in your list:\n"
                + taskHandler.tasklistToString(taskHandler.findTasks(this.input)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FindCommand f) {
            return this.input.equals(f.input);
        }
        return false;
    }
}
