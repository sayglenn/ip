package ginger.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ginger.command.ByeCommand;
import ginger.command.DeadlineCommand;
import ginger.command.DeleteCommand;
import ginger.command.EventCommand;
import ginger.command.FindCommand;
import ginger.command.HelpCommand;
import ginger.command.ListCommand;
import ginger.command.MarkCommand;
import ginger.command.ToDoCommand;
import ginger.command.UnmarkCommand;
import ginger.exception.IllegalGingerArgumentException;
import ginger.exception.IllegalGingerCommandException;

public class InputParserTest {

    @Test
    public void testInputParser_unknownCommand_exceptionThrown() throws IllegalGingerArgumentException {
        try {
            InputParser.parse("create art");
        } catch (IllegalGingerCommandException e) {
            assertEquals(e.getMessage(), "Oh no! This command does not exist. "
                    + "Try again or enter help for a list of commands.");
        }
    }

    @Test
    public void testInputParser_returnsByeCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("bye"), new ByeCommand());
    }

    @Test
    public void testInputParser_returnsHelpCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("help"), new HelpCommand());
    }

    @Test
    public void testInputParser_returnsListCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("list"), new ListCommand());
    }

    @Test
    public void testInputParser_returnsMarkCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("mark 1"), new MarkCommand(0));
    }

    @Test
    public void testInputParser_returnsUnmarkCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("unmark 1"), new UnmarkCommand(0));
    }

    @Test
    public void testInputParser_returnsDeleteCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("delete 1"), new DeleteCommand(0));
    }

    @Test
    public void testInputParser_returnsFindCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("find book"), new FindCommand("book"));
    }

    @Test
    public void testInputParser_returnsToDoCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("todo buy groceries"), new ToDoCommand("buy groceries"));
    }

    @Test
    public void testInputParser_toDoMissingTitle_exceptionThrown() throws IllegalGingerCommandException {
        try {
            InputParser.parse("todo");
        } catch (IllegalGingerArgumentException e) {
            assertEquals(e.getMessage(), "Oh no! Please follow the format of the command!\n"
                    + "Example usage: todo <title>");
        }
    }

    @Test
    public void testInputParser_returnsDeadlineCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("deadline assignment /by 20/08/2024 2100"),
                new DeadlineCommand("assignment",
                        LocalDateTime.of(2024, 8, 20, 21, 0)));
    }

    @Test
    public void testInputParser_deadlineMissingTime_exceptionThrown() throws IllegalGingerCommandException {
        try {
            InputParser.parse("deadline assignment /by");
        } catch (IllegalGingerArgumentException e) {
            assertEquals(e.getMessage(), "Oh no! Please follow the format of the command!\n"
                    + "Example usage: deadline <title> /by <time>");
        }
    }

    @Test
    public void testInputParser_returnsEventCommandCorrectly() throws IllegalGingerCommandException,
            IllegalGingerArgumentException {
        assertEquals(InputParser.parse("event dinner /from 10/10/2024 1800 /to 10/10/2024 2000"),
                new EventCommand("dinner",
                        LocalDateTime.of(2024, 10, 10, 18, 0),
                        LocalDateTime.of(2024, 10, 10, 20, 0)));
    }

    @Test
    public void testInputParser_eventMissingTitle_exceptionThrown() throws IllegalGingerCommandException {
        try {
            InputParser.parse("event /from 12/12/2024 1800 /to 12/12/2024 2100");
        } catch (IllegalGingerArgumentException e) {
            assertEquals(e.getMessage(), "Oh no! Please follow the format of the command!\n"
                    + "Example usage: event <title> /from <time> /to <time>");
        }
    }

    @Test
    public void testInputParser_eventMissingStartTime_exceptionThrown() throws IllegalGingerCommandException {
        try {
            InputParser.parse("event dinner /from /to 12/12/2024 2100");
        } catch (IllegalGingerArgumentException e) {
            assertEquals(e.getMessage(), "Oh no! Please follow the format of the command!\n"
                    + "Example usage: event <title> /from <time> /to <time>");
        }
    }
}
