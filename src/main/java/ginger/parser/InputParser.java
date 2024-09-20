package ginger.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import ginger.command.ByeCommand;
import ginger.command.Command;
import ginger.command.DeadlineCommand;
import ginger.command.DeleteCommand;
import ginger.command.EventCommand;
import ginger.command.FindCommand;
import ginger.command.HelpCommand;
import ginger.command.ListCommand;
import ginger.command.MarkCommand;
import ginger.command.TagCommand;
import ginger.command.ToDoCommand;
import ginger.command.UnmarkCommand;
import ginger.exception.IllegalGingerArgumentException;
import ginger.exception.IllegalGingerCommandException;

/**
 * Represents an InputParser, which will parse the user's inputs and return the appropriate command to the user
 * to be executed.
 */
public class InputParser {
    enum CommandList {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, HELP, DELETE, FIND, TAG;

        /**
         * Gets the specific command based on the given input of the user.
         * @param input The given input from the user.
         * @return The command input by the user.
         * @throws IllegalGingerCommandException If the command given does not match with any valid command.
         */
        public static CommandList getCommand(String input) throws IllegalGingerCommandException {
            try {
                return CommandList.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalGingerCommandException(
                        "Oh no! This command does not exist. Try again or enter help for a list of commands.");
            }
        }
    }

    /**
     * Parses the input from the user and returns the appropriate command.
     * @param input The input from the user to be parsed.
     * @return The command given by the user.
     * @throws IllegalGingerCommandException If the user input an unknown command.
     * @throws IllegalGingerArgumentException If the user input incorrect arguments with the command.
     */
    public static Command parse(String input) throws IllegalGingerCommandException, IllegalGingerArgumentException {
        String[] inputParts = input.split(" ", 2);
        CommandList givenCommand = CommandList.getCommand(inputParts[0]);
        assert givenCommand != null;

        return switch (givenCommand) {
        case BYE -> new ByeCommand();
        case HELP -> new HelpCommand();
        case LIST -> new ListCommand();
        case MARK -> handleMark(inputParts);
        case UNMARK -> handleUnmark(inputParts);
        case FIND -> handleFind(inputParts);
        case DELETE -> handleDelete(inputParts);
        case TODO -> handleToDo(inputParts);
        case DEADLINE -> handleDeadline(inputParts);
        case EVENT -> handleEvent(inputParts);
        case TAG -> handleTag(inputParts);
        default -> throw new IllegalGingerCommandException(
                "Oh no! This command does not exist. Try again or enter help for a list of commands.");
        };
    }

    private static Command handleMark(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "mark <task number>");
        return new MarkCommand(parseNumber(inputParts[1].trim()));
    }

    private static Command handleUnmark(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "unmark <task number>");
        return new UnmarkCommand(parseNumber(inputParts[1].trim()));
    }

    private static Command handleFind(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "find <task title>");
        return new FindCommand(inputParts[1].trim());
    }

    private static Command handleDelete(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "delete <task number>");
        return new DeleteCommand(parseNumber(inputParts[1].trim()));
    }

    private static Command handleToDo(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "todo <title>");
        return new ToDoCommand(inputParts[1].trim());
    }

    private static Command handleTag(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "tag <task number> <tag name>");
        String[] tagParts = inputParts[1].trim().split(" ", 2);
        validateArguments(tagParts, "tag <task number> <tag name>");
        return new TagCommand(parseNumber(tagParts[0].trim()), tagParts[1].trim());
    }

    /**
     * Parses a string into a number and returns the inputted number.
     * @param input The input from the user.
     * @return The number from the input string.
     * @throws IllegalGingerArgumentException If the input given is not a number.
     */
    private static int parseNumber(String input) throws IllegalGingerArgumentException {
        try {
            int number = Integer.parseInt(input);
            return number - 1;
        } catch (NumberFormatException e) {
            throw new IllegalGingerArgumentException("Please enter a proper number for the command!");
        }
    }

    /**
     * Parses an input if determined to be a deadline and returns an DeadlineCommand.
     * @param inputParts The input parts about the deadline from the user.
     * @return The DeadlineCommand based on user input.
     * @throws IllegalGingerArgumentException If the deadline arguments are incorrectly formatted.
     */
    private static Command handleDeadline(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "deadline <title> /by <time>");
        String input = inputParts[1].trim();
        String[] deadlineParts = input.split("/by");
        validateArguments(deadlineParts, "deadline <title> /by <time>");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime deadline = LocalDateTime.parse(deadlineParts[1].trim(), formatter);
            return new DeadlineCommand(deadlineParts[0].trim(), deadline);
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm"
                            + "\nExample usage: 01/08/2023 1830");
        }
    }

    /**
     * Parses an input if determined to be an event and returns an EventCommand.
     * @param inputParts The input parts about the event from the user.
     * @return The EventCommand based on user input.
     * @throws IllegalGingerArgumentException If the event arguments are incorrectly formatted.
     */
    private static Command handleEvent(String[] inputParts) throws IllegalGingerArgumentException {
        validateArguments(inputParts, "event <title> /from <time> /to <time>");
        String input = inputParts[1].trim();
        String[] eventParts = input.split("/from");
        validateArguments(eventParts, "event <title> /from <time> /to <time>");
        String[] timeParts = eventParts[1].split("/to", 2);
        validateArguments(timeParts, "event <title> /from <time> /to <time>");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime start = LocalDateTime.parse(timeParts[0].trim(), formatter);
            LocalDateTime end = LocalDateTime.parse(timeParts[1].trim(), formatter);
            return new EventCommand(eventParts[0].trim(), start, end);
        } catch (DateTimeParseException e) {
            throw new IllegalGingerArgumentException(
                    "Oh no! Your dates were incorrectly input. Please follow the format: dd/mm/yyyy HHmm"
                            + "\nExample usage: 01/08/2023 1830");
        }
    }

    /**
     * Checks if the number of inputs is less than 2 or if the second input is empty upon being split. This prevents
     * empty titles or empty dates from being input together with the command.
     * @param input The inputs of the array after splitting.
     * @param message The error message to be thrown.
     * @throws IllegalGingerArgumentException If the arguments are not properly formatted
     */
    private static void validateArguments(String[] input, String message) throws IllegalGingerArgumentException {
        if (input.length < 2 || input[1].trim().isEmpty() || input[0].trim().isEmpty()) {
            throw new IllegalGingerArgumentException("Oh no! Please follow the format of the command!\n"
                    + "Example usage: " + message);
        }
    }
}
