package by.aginskiy.application.model.dao;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    /**
     * Checks if the such login is already exist.
     *
     * @param login login for verification.
     * @return true if such login is already exist, otherwise - false.
     * @throws DaoException if the database throws SQLException.
     */
    boolean existsLogin(String login) throws DaoException;

    /**
     * Checks if the such login is already exist ignore current user.
     *
     * @param userId userId for verification.
     * @param login login for verification.
     * @return true if such login is already exist, otherwise - false.
     * @throws DaoException if the database throws SQLException.
     */
    boolean existsLoginIgnoreUser(int userId, String login) throws DaoException;

    /**
     * Registers the user.
     *
     * @param user user object for registration.
     * @param password user password as String object for registration.
     * @throws DaoException if the database throws SQLException.
     */
    void register(User user, String password) throws DaoException;

    /**
     * Finds the user by the login, if the password is correct.
     *
     * @param login user's login as String object.
     * @param password user password as String object.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<User> login(String login, String password) throws DaoException;

    /**
     * Updates user photo.
     *
     * @param userId id of the user.
     * @param photoName the name of the photo as String object.
     * @throws DaoException if the database throws SQLException.
     */
    void updateUserPhoto(int userId, String photoName) throws DaoException;

    /**
     * Search user's password.
     *
     * @param userId id of the user.
     * @return String object with the user password if such user is exist, otherwise - an empty String.
     * @throws DaoException if the database throws SQLException.
     */
    String searchUserPassword(int userId) throws DaoException;

    /**
     * Blocks the user.
     *
     * @param userId id of the user to be blocked.
     * @throws DaoException if the database throws SQLException.
     */
    void blockUser(int userId) throws DaoException;

    /**
     * Unblocks the user.
     *
     * @param userId id of the user to be unblocked.
     * @throws DaoException if the database throws SQLException.
     */
    void unblockUser(int userId) throws DaoException;

    /**
     * Updates the user.
     *
     * @param user User object to update.
     * @throws DaoException if the database throws SQLException.
     */
    void updateUser(User user) throws DaoException;

    /**
     * Updates the user's password.
     *
     * @param userId id of the user to be updated.
     * @param password new user password as String object.
     * @throws DaoException if the database throws SQLException.
     */
    void updatePassword(int userId, String password) throws DaoException;

    /**
     * Finds user by id.
     *
     * @param userId id of the user to search.
     * @return Optional with User object if such user is exist, otherwise - empty optional.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<User> findById(int userId) throws DaoException;

    /**
     * Finds all user.
     *
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found users, if the users are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<User> findAll(int start, int limit) throws DaoException;

    /**
     * Finds all users by the criteria.
     *
     * @param criteria search criteria as String object.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found users, if the users are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<User> findAllByCriteria(String criteria, int start, int limit) throws DaoException;

    /**
     * Counts the number of all users.
     *
     * @return number of users.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateUsers() throws DaoException;

    /**
     * Counts the number of all users by the search criteria.
     *
     * @param criteria search criteria as String object.
     * @return number of users found by the criteria.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateUsersByCriteria(String criteria) throws DaoException;
}
