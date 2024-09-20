package ginger.command;

import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents a HelpCommand which displays the list of available commands when executed.
 */
public class HelpCommand extends Command {
    private final String helpMessage = "Need a pinch of info? I'm here to help!\n"
            + "bye - Exits the Ginger chatbot.\n\n"
            + "list - Lists the current tasks.\n\n"
            + "mark - Marks a task as complete. Usage: mark <task number>\n\n"
            + "unmark - Marks a task as incomplete. Usage: unmark <task number>\n\n"
            + "todo - Adds a To Do task. Usage: todo <title>\n\n"
            + "deadline - Adds a Deadline task which includes a deadline. Usage: deadline <title> /by <time>\n\n"
            + "event - Adds an Event task which has start, end time. Usage: event <title> /from <time> /to <time>\n\n"
            + "help - Displays this set of information again.\n\n"
            + "delete - Deletes a task from the list. Usage: delete <task number>\n\n"
            + "find - Finds all tasks with the given input from the list. Usage: find <task name>\n\n"
            + "Do note that Ginger supports date and time formatting. Please follow the convention dd/mm/yyyy HHmm\n\n"
            + "when entering your time! Examples: 01/08/2024 1830, 1/8/2024 1830 is also accepted.";

    @Override
    public String execute(TaskHandler taskHandler, Ui ui) {
        ui.outputMessage(helpMessage);
        return helpMessage;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof HelpCommand;
    }
}
