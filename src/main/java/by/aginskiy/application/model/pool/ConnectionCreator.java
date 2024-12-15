package by.aginskiy.application.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class used to create Connection objects.
 *
 * @author Dzmitry Ahinski
 */
public class ConnectionCreator {

    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_PATH = "/config/database_config.properties";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";

    static {
        try {
            properties.load(ConnectionCreator.class.getResourceAsStream(PROPERTIES_PATH));
            String driver = properties.getProperty(DRIVER_PROPERTY);
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException exception) {
            logger.log(Level.FATAL, "Error creating connection", exception);
        }
    }

    private ConnectionCreator() {

    }

    /**
     * Creates a Connection object.
     *
     * @return Connection object.
     * @throws SQLException if a database access error occurs or the url is null.
     */
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty(URL_PROPERTY), properties);
    }
}
