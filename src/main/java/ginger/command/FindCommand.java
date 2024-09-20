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
    public String execute(TaskHandler taskHandler, Ui ui) throws IllegalGingerArgumentException {
        String message = "Simmering on that one, give me a sec!\nHere are the matching tasks in your list:\n"
                + taskHandler.taskListToString(taskHandler.findTasks(this.input));
        ui.outputMessage(message);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FindCommand f) {
            return this.input.equals(f.input);
        }
        return false;
    }
}
