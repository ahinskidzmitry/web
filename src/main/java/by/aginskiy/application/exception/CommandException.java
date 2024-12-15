package by.aginskiy.application.exception;

/**
 * An exception that provides information on errors occurred while processing a command.
 *
 * @author Dzmitry Ahinski
 */
public class CommandException extends Exception {

    /**
     * Constructs a CommandException object with a given message.
     *
     * @param message String object of the given message.
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a CommandException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public CommandException(Throwable cause) {
        super(cause);
    }
}
