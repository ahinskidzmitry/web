package by.aginskiy.application.model.pool;

import by.aginskiy.application.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-Safe Singleton. Used to contain, give and manage Connection objects. Has dynamic size from 8 to 16.
 *
 * @author Dzmitry Ahinski
 */

public enum ConnectionPool {

    INSTANCE;
    private final Logger logger = LogManager.getLogger();
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenConnections;
    private static Lock locker = new ReentrantLock(true);
    private static final int MINIMAL_POOL_SIZE = 8;
    private static final int MAXIMAL_POOL_SIZE = 16;
    private static final int MAX_ERROR_NUMBER = 3;

    ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(MINIMAL_POOL_SIZE);
        givenConnections = new ArrayDeque<>(MINIMAL_POOL_SIZE);
        int errorCounter = 0;
        for(int i = 0; i < MINIMAL_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Error when trying to create a connection", exception);
                ++errorCounter;
            }
        }
        if(errorCounter >= MAX_ERROR_NUMBER) {
            logger.log(Level.FATAL, "Number of connections, that are not been created - " + errorCounter);
            throw new RuntimeException("Number of connections, that are not been created - " + errorCounter);
        }
        logger.log(Level.INFO, "Connection pool has been filled");
    }

    /**
     * Gives a Connection object from the pool.
     *
     * @return Connection object.
     * @throws ConnectionPoolException if InterruptedException was thrown while processing.
     */
    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        if(freeConnections.isEmpty() && givenConnections.size() < MAXIMAL_POOL_SIZE) {
            try {
                ProxyConnection newConnection = new ProxyConnection(ConnectionCreator.createConnection());
                givenConnections.offer(newConnection);
                connection = newConnection;
                logger.log(Level.DEBUG, "Connection pool expanded to " + givenConnections.size());
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Error when trying to give a connection", exception);
                throw new ConnectionPoolException(exception);
            }
        } else {
            try {
                connection = freeConnections.take();
                givenConnections.offer(connection);
                logger.log(Level.DEBUG, "Connection has been given");
            } catch (InterruptedException exception) {
                logger.log(Level.ERROR, "Connection has not been given", exception);
                throw new ConnectionPoolException(exception);
            }
        }
        return connection;
    }

    /**
     * Returns connection object to the pool.
     *
     */
    public void releaseConnection(Connection connection) {
        if(connection instanceof ProxyConnection && givenConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
            logger.log(Level.INFO, "Connection has been released");
        } else {
            logger.log(Level.ERROR, "Connection has not been released");
        }
    }

    /**
     * Destroys a connection pool.
     *
     */
    public void destroyPool() throws ConnectionPoolException {
        try {
            int poolSize = freeConnections.size() + givenConnections.size();
            for(int i = 0; i < poolSize; i++) {
                ProxyConnection connection = freeConnections.take();
                connection.reallyClose();
            }
            logger.log(Level.DEBUG, "Pool has been destroyed");
        } catch (InterruptedException | SQLException exception) {
            logger.log(Level.ERROR, "Pool has not been destroyed", exception);
            throw new ConnectionPoolException(exception);
        } finally {
            deregisterDrivers();
        }
    }

    /**
     * Used for deregistering drivers.
     *
     * @throws ConnectionPoolException if SQLException was thrown while processing.
     */
    private void deregisterDrivers() throws ConnectionPoolException {
        try {
            while (DriverManager.getDrivers().hasMoreElements()) {
                Driver driver = DriverManager.getDrivers().nextElement();
                DriverManager.deregisterDriver(driver);
            }
            logger.log(Level.DEBUG, "Drivers has been deregistered");
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Drivers has not been deregistered");
            throw new ConnectionPoolException(exception);
        }
    }
}
