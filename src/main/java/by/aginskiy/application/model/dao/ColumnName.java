package by.aginskiy.application.model.dao;

/**
 * Class contains constant names of tables' columns.
 *
 * @author Dzmitry Ahinski
 */
public class ColumnName {

    /**
     * Represents user's ID column at 'users' table.
     */
    public static final String USER_ID = "id";

    /**
     * Represents user's name column at 'users' table.
     */
    public static final String USER_NAME = "name";

    /**
     * Represents user's email column at 'users' table.
     */
    public static final String USER_EMAIL = "email";

    /**
     * Represents user's login column at 'users' table.
     */
    public static final String USER_LOGIN = "login";

    /**
     * Represents user's password column at 'users' table.
     */
    public static final String USER_PASSWORD = "password";

    /**
     * Represents user's photo name column at 'users' table.
     */
    public static final String USER_PHOTO_NAME = "photo_name";

    /**
     * Represents user's role column at 'users' table.
     */
    public static final String USER_ROLE = "role";

    /**
     * Represents article's id column at 'articles' table.
     */
    public static final String ARTICLE_ID = "id";

    /**
     * Represents article's topic column at 'articles' table.
     */
    public static final String ARTICLE_TOPIC = "topic";

    /**
     * Represents article's date column at 'articles' table.
     */
    public static final String ARTICLE_DATE = "date";

    /**
     * Represents article's text column at 'articles' table.
     */
    public static final String ARTICLE_TEXT = "text";

    /**
     * Represents comment's id column at 'comments' table.
     */
    public static final String COMMENT_ID = "id";

    /**
     * Represents comment's text column at 'comments' table.
     */
    public static final String COMMENT_TEXT = "text";

    /**
     * Represents comment's date column at 'comments' table.
     */
    public static final String COMMENT_DATE = "date";

    /**
     * Represents user's like value column at 'rating' table.
     */
    public static final String LIKE_VALUE = "value";

    /**
     * Represents is the entity active at 'comments', 'articles' tables.
     */
    public static final String IS_ACTIVE = "is_active";

    /**
     * Represents is the entity blocked at 'comments', 'articles', 'users' tables.
     */
    public static final String IS_BLOCKED = "is_blocked";

    private ColumnName() {

    }
}
