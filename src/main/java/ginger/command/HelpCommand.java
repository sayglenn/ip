package ginger.command;

import ginger.task.TaskHandler;
import ginger.ui.Ui;

public class HelpCommand extends Command {
    private final String helpMessage = "Here is a list of commands to get you started:\n" +
            "bye - Exits the Ginger chatbot.\n" +
            "list - Lists the current tasks.\n" +
            "mark - Marks a task as complete. Usage: mark <task number>\n" +
            "unmark - Marks a task as incomplete. Usage: unmark <task number>\n" +
            "todo - Adds a To Do task. Usage: todo <title>\n" +
            "deadline - Adds a Deadline task which includes a deadline. Usage: deadline <title> /by <time>\n" +
            "event - Adds an Event task which has start and end time. Usage: event <title> /from <time> /to <time>\n" +
            "help - Displays this set of information again.\n" +
            "delete - Deletes a task from the list. Usage: delete <task number>\n\n" +
            "Do note that Ginger supports date and time formatting. Please follow the convention dd/mm/yyyy HHmm\n" +
            "when entering your time! Examples: 01/08/2024 1830, 1/8/2024 1830 is also accepted.";

    @Override
    public void execute(TaskHandler taskHandler, Ui ui) {
        ui.outputMessage(helpMessage);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof HelpCommand;
    }
}
