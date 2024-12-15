package by.aginskiy.application.model.service.impl;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.dao.impl.UserDaoImpl;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.service.UserService;
import by.aginskiy.application.model.util.PasswordEncryptor;
import by.aginskiy.application.model.util.mail.MailSender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserService implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum UserServiceImpl implements UserService {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();
    private static final String MAIL_SUBJECT = "You have been registered on AQuestions";
    private static final String MAIL_MESSAGE = "Thank you for registering on AQuestions.";

    @Override
    public boolean existsLoginIgnoreUser(int userId, String login) throws ServiceException {
        boolean isExists = false;
        try {
            isExists = UserDaoImpl.INSTANCE.existsLoginIgnoreUser(userId, login);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to check login existence ignore current user, ", exception);
            throw new ServiceException(exception);
        }
        return isExists;
    }

    @Override
    public boolean register(User user, String password) throws ServiceException {
        boolean isRegistered = false;
        try {
            if(!UserDaoImpl.INSTANCE.existsLogin(user.getLogin())) {
                String encryptedPassword = PasswordEncryptor.encrypt(password);
                UserDaoImpl.INSTANCE.register(user, encryptedPassword);
                isRegistered = true;
                logger.log(Level.DEBUG, "User has been registered, " + user);
            } else {
                isRegistered = false;
                logger.log(Level.DEBUG, "User has not been registered, because such user is already exist, " + user);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to register user, ", exception);
            throw new ServiceException(exception);
        }
        return isRegistered;
    }

    @Override
    public void blockUser(int userId) throws ServiceException {
        try {
            UserDaoImpl.INSTANCE.blockUser(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to block user, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void unblockUser(int userId) throws ServiceException {
        try {
            UserDaoImpl.INSTANCE.unblockUser(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to unblock user, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        Optional<User> user = Optional.empty();
        try {
            user = UserDaoImpl.INSTANCE.login(login, password);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to sign in, ", exception);
            throw new ServiceException(exception);
        }
        return user;
    }

    @Override
    public void updateUserPhoto(int userId, String photoName) throws ServiceException {
        try {
            UserDaoImpl.INSTANCE.updateUserPhoto(userId, photoName);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to update user photo, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public boolean isPasswordCanBeChanged(int userId, String password, String confirmedPassword, String newPassword,
                                          String confirmedNewPassword) throws ServiceException {
        boolean isCanBeChanged = false;
        try {
            String userPassword = UserDaoImpl.INSTANCE.searchUserPassword(userId);
            isCanBeChanged = newPassword.equals(confirmedNewPassword) && password.equals(confirmedPassword)
                    && PasswordEncryptor.check(password, userPassword);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to check if password can be changed, ", exception);
            throw new ServiceException(exception);
        }
        return isCanBeChanged;
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            UserDaoImpl.INSTANCE.updateUser(user);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to update user, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void updatePassword(int userId, String password) throws ServiceException {
        try {
            String encryptedPassword = PasswordEncryptor.encrypt(password);
            UserDaoImpl.INSTANCE.updatePassword(userId, encryptedPassword);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to update user password, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Optional<User> findById(int userId) throws ServiceException {
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = UserDaoImpl.INSTANCE.findById(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find user by id, ", exception);
            throw new ServiceException(exception);
        }
        return userOptional;
    }

    @Override
    public List<User> findAll(int start, int limit) throws ServiceException {
        List<User> users = new ArrayList<>();
        try {
            users = UserDaoImpl.INSTANCE.findAll(start, limit);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find all users, ", exception);
            throw new ServiceException(exception);
        }
        return users;
    }

    @Override
    public List<User> findAllByCriteria(String criteria, int start, int limit) throws ServiceException {
        List<User> users = new ArrayList<>();
        try {
            users = UserDaoImpl.INSTANCE.findAllByCriteria(criteria, start, limit);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find all users by criteria, ", exception);
            throw new ServiceException(exception);
        }
        return users;
    }

    @Override
    public int calculateUsers() throws ServiceException {
        int usersNumber = 0;
        try {
            usersNumber = UserDaoImpl.INSTANCE.calculateUsers();
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate users, ", exception);
            throw new ServiceException(exception);
        }
        return usersNumber;
    }

    @Override
    public int calculateUsersByCriteria(String criteria) throws ServiceException {
        int usersNumber = 0;
        try {
            usersNumber = UserDaoImpl.INSTANCE.calculateUsersByCriteria(criteria);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate users by criteria, ", exception);
            throw new ServiceException(exception);
        }
        return usersNumber;
    }

    @Override
    public void sendMail(String recipient) {
        MailSender sender = new MailSender(recipient, MAIL_SUBJECT, MAIL_MESSAGE);
        sender.sendMessage();
    }
}
