package ginger.exception;

/**
 * Represents an IllegalGingerArgumentException which is to be thrown if arguments to commands are
 * incorrectly formatted.
 */
public class IllegalGingerArgumentException extends Exception {
    public IllegalGingerArgumentException(String message) {
        super(message);
    }
}
