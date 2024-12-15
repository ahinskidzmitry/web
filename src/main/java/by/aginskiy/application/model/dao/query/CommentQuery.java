package by.aginskiy.application.model.dao.query;

/**
 * Provides queries to work with comments.
 *
 * @author Dzmitry Ahinski
 */
public class CommentQuery {

    public static final String ADDING_COMMENT_QUERY = "INSERT INTO comments (text, date, users_id, articles_id)"
            + " values (?, NOW(), ?, ?);";

    public static final String IS_LIKED_BY_USER_QUERY = "SELECT COUNT(1) FROM likes WHERE users_id = ? AND comments_id = ?;";

    public static final String ADD_LIKE_QUERY = "INSERT INTO likes (users_id, comments_id) VALUES (?, ?);";

    public static final String REMOVE_LIKE_QUERY = "DELETE FROM likes WHERE users_id = ? AND comments_id = ?;";

    public static final String BLOCK_COMMENT_QUERY = "UPDATE comments SET is_blocked = 1 WHERE comments.id = ?;";

    public static final String UNBLOCK_COMMENT_QUERY = "UPDATE comments SET is_blocked = 0 WHERE comments.id = ?;";

    public static final String HIDE_COMMENT_QUERY = "UPDATE comments SET is_active = 0 WHERE comments.id = ?;";

    public static final String RECOVER_COMMENT_QUERY = "UPDATE comments SET is_active = 1 WHERE comments.id = ?;";

    public static final String CALCULATE_LIKES_QUERY = "SELECT COUNT(*) from likes WHERE comments_id = ?;";

    public static final String CALCULATE_ACTIVE_COMMENTS_QUERY = "SELECT COUNT(*) FROM comments WHERE articles_id = ? AND"
            + " comments.is_active = 1 AND comments.is_blocked = 0;";

    public static final String CALCULATE_COMMENTS_QUERY = "SELECT COUNT(*) FROM comments WHERE articles_id = ?;";

    public static final String CALCULATE_USER_COMMENTS_QUERY = "SELECT COUNT(*) from comments WHERE users_id = ?;";

    public static final String UPDATE_COMMENT_TEXT_QUERY = "UPDATE comments SET text = ? WHERE id = ?;";

    public static final String FIND_BY_ID_QUERY = "SELECT comments.*, users.name, users.photo_name FROM comments INNER JOIN"
            + " users ON users.id = comments.users_id WHERE comments.id = ? LIMIT 1;";

    public static final String FINDING_USER_COMMENTS_QUERY = "SELECT comments.*, users.name, users.photo_name"
            + " FROM comments INNER JOIN users ON users.id = comments.users_id WHERE comments.users_id = ?"
            + " ORDER BY comments.id DESC LIMIT ?, ?;";

    public static final String FINDING_ACTIVE_COMMENTS_QUERY = "SELECT comments.*, users.name, users.photo_name"
            + " FROM comments INNER JOIN users ON users.id = comments.users_id WHERE comments.articles_id = ?"
            + " AND comments.is_active = 1 AND comments.is_blocked = 0 ORDER BY comments.id DESC LIMIT ?, ?;";

    public static final String FINDING_COMMENTS_QUERY = "SELECT comments.*, users.name, users.photo_name"
            + " FROM comments INNER JOIN users ON users.id = comments.users_id WHERE comments.articles_id = ?"
            + " ORDER BY comments.id DESC LIMIT ?, ?;";

    private CommentQuery() {

    }
}
