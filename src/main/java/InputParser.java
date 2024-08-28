import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputParser {
    enum CommandList {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, HELP, DELETE;

        public static CommandList getCommand(String command) throws IllegalGingerCommandException {
            try {
                return CommandList.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalGingerCommandException(
                        "Oh no! This command does not exist. Try again or enter help for a list of commands.");
            }
        }
    }

    public static Command parse(String input) throws IllegalGingerCommandException, IllegalGingerArgumentException {
        String[] inputParts = input.split(" ", 2);
        CommandList givenCommand = CommandList.getCommand(inputParts[0]);

        switch (givenCommand) {
        case BYE -> {
            return new ByeCommand();
        }
        case HELP -> {
            return new HelpCommand();
        }
        case LIST -> {
            return new ListCommand();
        }
        case MARK -> {
            if (inputParts.length < 2) {
                throw new IllegalGingerArgumentException("Oh no! There are insufficient arguments here!\n" +
                        "Example usage: mark <task number>");
            }
            int index = parseNumber(inputParts[1].trim());
            return new MarkCommand(index);
        }
        case UNMARK -> {
            if (inputParts.length < 2) {
                throw new IllegalGingerArgumentException("Oh no! There are insufficient arguments here!\n" +
                        "Example usage: unmark <task number>");
            }
            int index = parseNumber(inputParts[1].trim());
            return new UnmarkCommand(index);
        }
        case DELETE -> {
            if (inputParts.length < 2) {
                throw new IllegalGingerArgumentException("Oh no! There are insufficient arguments here!\n" +
                        "Example usage: delete <task number>");
            }
            int index = parseNumber(inputParts[1].trim());
            return new DeleteCommand(index);
        }
        case TODO -> {
            if (inputParts.length < 2) {
                throw new IllegalGingerArgumentException("Oh no! There are insufficient arguments here!\n" +
                        "Example usage: todo <title>");
            }
            return new ToDoCommand(inputParts[1].trim());
        }
        case DEADLINE -> {
            if (inputParts.length < 2) {
                throw new IllegalGingerArgumentException("Oh no! There are insufficient arguments here!\n" +
                        "Example usage: deadline <title> /by <time>");
            }
            return parseDeadline(inputParts[1].trim());

        }
        case EVENT -> {
            if (inputParts.length < 2) {
                throw new IllegalGingerArgumentException("Oh no! There are insufficient arguments here!\n" +
                        "Example usage: event <title> /from <time> /to <time>");
            }
            return parseEvent(inputParts[1].trim());
        }
        }
        throw new IllegalGingerCommandException(
                "Oh no! This command does not exist. Try again or enter help for a list of commands.");
    }

    private static int parseNumber(String input) throws IllegalGingerArgumentException {
        try {
            int number = Integer.parseInt(input);
            return number - 1;
        } catch (NumberFormatException e) {
            throw new IllegalGingerArgumentException("Please enter a proper number for the command!");
        }
    }

    private static Command parseDeadline(String input) throws IllegalGingerArgumentException {
        String[] deadlineParts = input.split("/by");
        if (deadlineParts.length < 2) {
            throw new IllegalGingerArgumentException("Please follow the format of the command!\n" +
                    "Example usage: deadline <title> /by <time>");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime deadline = LocalDateTime.parse(deadlineParts[1].trim(), formatter);
            return new DeadlineCommand(deadlineParts[0].trim(), deadline);
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm" +
                            "\nExample usage: 01/08/2023 1830");
        }
    }

    private static Command parseEvent(String input) throws IllegalGingerArgumentException {
        String[] eventParts = input.split("/from");
        if (eventParts.length < 2) {
            throw new IllegalGingerArgumentException("Please follow the format of the command!\n" +
                    "Example usage: event <title> /from <time> /to <time>");
        }

        String[] timeParts = eventParts[1].split("/to", 2);
        if (timeParts.length < 2) {
            throw new IllegalGingerArgumentException("Please follow the format of the command!\n" +
                    "Example usage: event <title> /from <time> /to <time>");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime start = LocalDateTime.parse(timeParts[0].trim(), formatter);
            LocalDateTime end = LocalDateTime.parse(timeParts[1].trim(), formatter);
            return new EventCommand(eventParts[0].trim(), start, end);
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm" +
                            "\nExample usage: 01/08/2023 1830");
        }
    }
}
