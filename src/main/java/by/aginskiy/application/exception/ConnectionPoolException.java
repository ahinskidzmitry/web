package by.aginskiy.application.exception;

/**
 * An exception that provides information on errors thrown by a ConnectionPool.
 *
 * @author Dzmitry Ahinski
 */
public class ConnectionPoolException extends Exception {

    /**
     * Constructs a ConnectionPoolException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
