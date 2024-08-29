package ginger.exception;

/**
 * Represents an IllegalGingerCommandException which is to be thrown if the command from the user is unknown.
 */
public class IllegalGingerCommandException extends Exception {
    public IllegalGingerCommandException(String message) {
        super(message);
    }
}
