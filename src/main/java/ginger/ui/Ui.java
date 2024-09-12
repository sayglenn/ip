package ginger.ui;

/**
 * Represents the UI of the bot, which will display replies to the user input in the terminal.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String BOT_NAME = "Ginger";

    /**
     * Prints the given message as a reply from the bot.
     * @param message The message to be printed by the user.
     */
    public void outputMessage(String message) {
        System.out.println(HORIZONTAL_LINE + "\n" + message + "\n" + HORIZONTAL_LINE);
    }

    /**
     * Prints the welcome message to greet the user upon starting of the bot.
     */
    public void welcomeMessage() {
        outputMessage(String.format("Hello! I'm %s\nHow can I spice things up for you?", BOT_NAME));
    }
}
