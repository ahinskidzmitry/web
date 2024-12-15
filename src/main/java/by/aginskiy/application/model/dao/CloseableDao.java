package by.aginskiy.application.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface used for closing Dao resources.
 *
 * @author Dzmitry Ahinski
 */
public interface CloseableDao {

    Logger logger = LogManager.getLogger();

    /**
     * Wrapper method of Statement's close() with catching an exception and a logger inside.
     *
     * @param statement Statement object to be closed.
     */
    default void close(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
                logger.log(Level.INFO, "Statement has been closed");
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Statement has not been closed", exception);
            }
        }
    }

    /**
     * Wrapper method of Connection's close() with catching an exception and a logger inside.
     *
     * @param connection Connection object to be closed.
     */
    default void close(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
                logger.log(Level.INFO, "Connection has been closed");
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Connection has not been closed", exception);
            }
        }
    }
}
