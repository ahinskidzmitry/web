package by.aginskiy.application.exception;

/**
 * An exception that provides information on errors thrown by Dao.
 *
 * @author Dzmitry Ahinski
 */
public class DaoException extends Exception {

    /**
     * Constructs a DaoException object with a given message.
     *
     * @param message String object of the given message.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructs a DaoException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
