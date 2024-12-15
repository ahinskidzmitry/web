package by.aginskiy.application.model.dao.impl;

import by.aginskiy.application.exception.ConnectionPoolException;
import by.aginskiy.application.model.dao.CloseableDao;
import by.aginskiy.application.model.dao.ColumnName;
import by.aginskiy.application.model.dao.UserDao;
import by.aginskiy.application.model.dao.query.UserQuery;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.entity.UserRole;
import by.aginskiy.application.model.pool.ConnectionPool;
import by.aginskiy.application.model.util.PasswordEncryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDao implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum UserDaoImpl implements UserDao, CloseableDao {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    public boolean existsLogin(String login) throws DaoException {
        boolean isExists = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.IS_EXISTS_LOGIN_QUERY);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            isExists = resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return isExists;
    }

    @Override
    public boolean existsLoginIgnoreUser(int userId, String login) throws DaoException {
        boolean isExists = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.IS_EXISTS_LOGIN_IGNORE_USER_QUERY);
            statement.setInt(1, userId);
            statement.setString(2, login);
            ResultSet resultSet = statement.executeQuery();
            isExists = resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return isExists;
    }

    public Optional<User> login(String login, String password) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.LOGIN_QUERY);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String encryptedPassword = resultSet.getString(ColumnName.USER_PASSWORD);
                boolean isRightPassword = PasswordEncryptor.check(password, encryptedPassword);
                if(isRightPassword) {
                    int userId = resultSet.getInt(ColumnName.USER_ID);
                    String name = resultSet.getString(ColumnName.USER_NAME);
                    String email = resultSet.getString(ColumnName.USER_EMAIL);
                    String userLogin = resultSet.getString(ColumnName.USER_LOGIN);
                    String photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                    boolean blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                    UserRole role = UserRole.valueOf(resultSet.getString(ColumnName.USER_ROLE));
                    User user = new User(userId, name, email, userLogin, photoName, blocked, role);
                    userOptional = Optional.of(user);
                }
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return userOptional;
    }

    public void register(User user, String password) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.REGISTER_QUERY);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getLogin());
            statement.setString(4, password);
            statement.setInt(5, user.getRole().ordinal() - 1);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public void updateUserPhoto(int userId, String photoName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.UPDATE_PHOTO_QUERY);
            statement.setString(1, photoName);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public String searchUserPassword(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        String encryptedPassword = "";
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.FIND_USER_PASSWORD_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                encryptedPassword = resultSet.getString(ColumnName.USER_PASSWORD);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
        return encryptedPassword;
    }

    @Override
    public void blockUser(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.BLOCK_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void unblockUser(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.UNBLOCK_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateUser(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.UPDATE_USER_QUERY);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getLogin());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public void updatePassword(int userId, String password) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.UPDATE_USER_PASSWORD_QUERY);
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public Optional<User> findById(int userId) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.FIND_BY_ID_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int currentUserId = resultSet.getInt(ColumnName.USER_ID);
                String name = resultSet.getString(ColumnName.USER_NAME);
                String login = resultSet.getString(ColumnName.USER_LOGIN);
                String email = resultSet.getString(ColumnName.USER_EMAIL);
                String photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                boolean isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                UserRole role = UserRole.valueOf(resultSet.getString(ColumnName.USER_ROLE));
                User user = new User(userId, name, email, login, photoName, isBlocked, role);
                userOptional = Optional.of(user);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return userOptional;
    }

    @Override
    public List<User> findAll(int start, int limit) throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.FIND_ALL_QUERY);
            statement.setInt(1, start);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            int userId = 0;
            String name = null;
            String email = null;
            String login = null;
            String photoName = null;
            boolean isBlocked = false;
            UserRole role = UserRole.CLIENT;
            User user = null;
            while(resultSet.next()) {
                userId = resultSet.getInt(ColumnName.USER_ID);
                name = resultSet.getString(ColumnName.USER_NAME);
                email = resultSet.getString(ColumnName.USER_EMAIL);
                login = resultSet.getString(ColumnName.USER_LOGIN);
                photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                role = UserRole.valueOf(resultSet.getString(ColumnName.USER_ROLE));
                user = new User(userId, name, email, login, photoName, isBlocked, role);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findAllByCriteria(String criteria, int start, int limit) throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.FIND_BY_CRITERIA);
            statement.setString(1, "%" + criteria + "%");
            statement.setString(2, "%" + criteria + "%");
            statement.setString(3, "%" + criteria + "%");
            statement.setInt(4, start);
            statement.setInt(5, limit);
            ResultSet resultSet = statement.executeQuery();
            int userId = 0;
            String name = null;
            String email = null;
            String login = null;
            String photoName = null;
            boolean isBlocked = false;
            UserRole role = UserRole.CLIENT;
            User user = null;
            while(resultSet.next()) {
                userId = resultSet.getInt(ColumnName.USER_ID);
                name = resultSet.getString(ColumnName.USER_NAME);
                email = resultSet.getString(ColumnName.USER_EMAIL);
                login = resultSet.getString(ColumnName.USER_LOGIN);
                photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                role = UserRole.valueOf(resultSet.getString(ColumnName.USER_ROLE));
                user = new User(userId, name, email, login, photoName, isBlocked, role);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return users;
    }

    @Override
    public int calculateUsers() throws DaoException {
        int usersNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.CALCULATE_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                usersNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return usersNumber;
    }

    @Override
    public int calculateUsersByCriteria(String criteria) throws DaoException {
        int usersNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserQuery.CALCULATE_ALL_USERS_BY_CRITERIA);
            statement.setString(1, "%" + criteria + "%");
            statement.setString(2, "%" + criteria + "%");
            statement.setString(3, "%" + criteria + "%");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                usersNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return usersNumber;
    }
}
