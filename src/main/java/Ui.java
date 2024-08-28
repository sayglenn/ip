public class Ui {
    private final String HORIZONTAL_LINE = "____________________________________________________________";
    private final String BOT_NAME = "Ginger";

    public void outputMessage(String message) {
        System.out.println(HORIZONTAL_LINE + "\n" + message + "\n" + HORIZONTAL_LINE);
    }

    public void welcomeMessage() {
        outputMessage(String.format("Hello! I'm %s\nHow can I spice things up for you?", BOT_NAME));
    }
}
