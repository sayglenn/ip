package ginger;

import java.util.Scanner;

import ginger.command.Command;
import ginger.exception.IllegalGingerArgumentException;
import ginger.exception.IllegalGingerCommandException;
import ginger.parser.InputParser;
import ginger.task.TaskHandler;
import ginger.ui.Ui;

/**
 * Represents the Ginger bot, which can take in user input and handle commands from the bot.
 */
public class Ginger {
    private final TaskHandler taskHandler;
    private final Ui ui;

    /**
     * Initialises a Ginger instance populates the respective taskHandler and ui fields.
     */
    public Ginger() {
        this.taskHandler = new TaskHandler();
        this.ui = new Ui();
    }

    /**
     * Initialises the bot for interaction with user.
     */
    public void run() {
        ui.welcomeMessage();
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            try {
                Command command = InputParser.parse(sc.nextLine());
                command.execute(this.taskHandler, this.ui);
                isExit = command.isExit();
            } catch (IllegalGingerCommandException | IllegalGingerArgumentException e) {
                ui.outputMessage(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Ginger().run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = InputParser.parse(input);
            return command.execute(this.taskHandler, this.ui);
        } catch (IllegalGingerCommandException | IllegalGingerArgumentException e) {
            return e.getMessage();
        }
    }
}
