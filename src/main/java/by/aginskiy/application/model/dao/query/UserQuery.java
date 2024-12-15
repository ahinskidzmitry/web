package by.aginskiy.application.model.dao.query;

/**
 * Provides queries to work with User entity
 *
 * @author Dzmitry Ahinski
 */
public class UserQuery {

    public static final String IS_EXISTS_LOGIN_QUERY = "SELECT COUNT(1) FROM users WHERE login = ?;";

    public static final String IS_EXISTS_LOGIN_IGNORE_USER_QUERY = "SELECT COUNT(1) FROM users WHERE id != ? AND login = ?";

    public static final String REGISTER_QUERY = "INSERT INTO users (name, email, login, password, photo_name, roles_id) values (?, ?, ?, ?, '', ?);";

    public static final String LOGIN_QUERY = "SELECT users.*, roles.role FROM users INNER JOIN roles ON"
            + " users.roles_id = roles.id WHERE login = ?;";

    public static final String BLOCK_USER_QUERY = "UPDATE users SET is_blocked = 1 WHERE id = ?;";

    public static final String UNBLOCK_USER_QUERY = "UPDATE users SET is_blocked = 0 WHERE id = ?;";

    public static final String FIND_BY_ID_QUERY = "SELECT users.*, roles.role FROM users INNER JOIN roles ON "
            + " users.roles_id = roles.id WHERE users.id = ?;";

    public static final String UPDATE_PHOTO_QUERY = "UPDATE users SET photo_name = ? WHERE id = ?;";

    public static final String FIND_USER_PASSWORD_QUERY = "SELECT password FROM users WHERE id = ?;";

    public static final String UPDATE_USER_QUERY = "UPDATE users SET name = ?, email = ?, login = ? WHERE id = ?;";

    public static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE users SET password = ? WHERE id = ?;";

    public static final String FIND_ALL_QUERY = "SELECT users.*, roles.role FROM users INNER JOIN roles ON"
            + " users.roles_id = roles.id ORDER BY users.id DESC LIMIT ?, ?;";

    public static final String FIND_BY_CRITERIA = "SELECT users.*, roles.role FROM users users INNER JOIN roles ON"
            + " users.roles_id = roles.id WHERE users.name LIKE ? OR email LIKE ? OR users.login LIKE ? ORDER BY users.id DESC LIMIT ?, ?;";

    public static final String CALCULATE_ALL_USERS = "SELECT COUNT(*) FROM users;";

    public static final String CALCULATE_ALL_USERS_BY_CRITERIA = "SELECT COUNT(*) FROM users WHERE name LIKE ? OR email LIKE ? OR login LIKE ?;";

    private UserQuery() {

    }
}
