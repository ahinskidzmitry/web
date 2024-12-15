package by.aginskiy.application.model.service;

import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on users.
 *
 * @author Dzmitry Ahinski
 */
public interface UserService {

    /**
     * Checks if the such login is already exist ignore current user.
     *
     * @param userId userId for verification.
     * @param login login for verification.
     * @return true if such login is already exist, otherwise - false.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean existsLoginIgnoreUser(int userId, String login) throws ServiceException;

    /**
     * Registers the user.
     *
     * @param user user object for registration.
     * @param password user password as String object for registration.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean register(User user, String password) throws ServiceException;

    /**
     * Blocks the user.
     *
     * @param userId id of the user to be blocked.
     * @throws ServiceException if an error occurs while processing.
     */
    void blockUser(int userId) throws ServiceException;

    /**
     * Unblocks the user.
     *
     * @param userId id of the user to be unblocked.
     * @throws ServiceException if an error occurs while processing.
     */
    void unblockUser(int userId) throws ServiceException;

    /**
     * Finds the user by the login, if the password is correct.
     *
     * @param login user's login as String object.
     * @param password user password as String object.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<User> login(String login, String password) throws ServiceException;

    /**
     * Updates user photo.
     *
     * @param userId id of the user.
     * @param photoName the name of the photo as String object.
     * @throws ServiceException if an error occurs while processing.
     */
    void updateUserPhoto(int userId, String photoName) throws ServiceException;

    /**
     * Checks if the user password can be changed.
     *
     * @param userId id of the user.
     * @param password current user password.
     * @param confirmedPassword confirmed user password.
     * @param newPassword new password.
     * @param confirmedNewPassword confirmed new password.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean isPasswordCanBeChanged(int userId, String password, String confirmedPassword, String newPassword,
                                   String confirmedNewPassword) throws ServiceException;

    /**
     * Updates the user.
     *
     * @param user User object to update.
     * @throws ServiceException if an error occurs while processing.
     */
    void updateUser(User user) throws ServiceException;

    /**
     * Updates the user's password.
     *
     * @param userId id of the user to be updated.
     * @param password new user password as String object.
     * @throws ServiceException if an error occurs while processing.
     */
    void updatePassword(int userId, String password) throws ServiceException;

    /**
     * Finds user by id.
     *
     * @param userId id of the user to search.
     * @return Optional with User object if such user is exist, otherwise - empty optional.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<User> findById(int userId) throws ServiceException;

    /**
     * Finds all user.
     *
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found users, if the users are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<User> findAll(int start, int limit) throws ServiceException;

    /**
     * Finds all users by the criteria.
     *
     * @param criteria search criteria as String object.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found users, if the users are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<User> findAllByCriteria(String criteria, int start, int limit) throws ServiceException;

    /**
     * Counts the number of all users.
     *
     * @return number of users.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateUsers() throws ServiceException;

    /**
     * Counts the number of all users by the search criteria.
     *
     * @param criteria search criteria as String object.
     * @return number of users found by the criteria.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateUsersByCriteria(String criteria) throws ServiceException;

    /**
     *
     * Sends the message to the current user's email.
     *
     */
    void sendMail(String recipient);
}
