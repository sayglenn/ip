package ginger.parser;

import ginger.command.*;
import ginger.exception.IllegalGingerArgumentException;
import ginger.exception.IllegalGingerCommandException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an InputParser, which will parse the user's inputs and return the appropriate command to the user
 * to be executed.
 */
public class InputParser {
    enum CommandList {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, HELP, DELETE;

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
     * Parses an input if determined to be an deadline and returns an DeadlineCommand.
     * @param input The input about the deadline from the user.
     * @return The DeadlineCommand based on user input.
     * @throws IllegalGingerArgumentException If the deadline arguments are incorrectly formatted.
     */
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

    /**
     * Parses an input if determined to be an event and returns an EventCommand.
     * @param input The input about the event from the user.
     * @return The EventCommand based on user input.
     * @throws IllegalGingerArgumentException If the event arguments are incorrectly formatted.
     */
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
