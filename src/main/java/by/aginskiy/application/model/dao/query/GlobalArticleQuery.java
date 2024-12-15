package by.aginskiy.application.model.dao.query;

/**
 * Provides queries to work with global articles.
 *
 * @author Dzmitry Ahinski
 */
public class GlobalArticleQuery {

    public static final String ADDING_ARTICLE_QUERY = "INSERT INTO articles (topic, users_id, date, text)"
            + "values (?, ?, NOW(), ?);";

    public static final String CALCULATE_ACTIVE_ARTICLES_QUERY = "SELECT COUNT(*) FROM articles WHERE articles.is_active = 1"
            + " AND articles.is_blocked = 0;";

    public static final String IS_BLOCKED_ARTICLE_QUERY = "SELECT is_blocked FROM articles WHERE articles.id = ?;";

    public static final String BLOCK_ARTICLE_QUERY = "UPDATE articles SET is_blocked = 1 WHERE id = ?;";

    public static final String UNBLOCK_ARTICLE_QUERY = "UPDATE articles SET is_blocked = 0 WHERE id = ?;";

    public static final String CALCULATE_ALL_ARTICLES_QUERY = "SELECT COUNT(*) FROM articles;";

    public static final String CALCULATE_ARTICLES_BY_CRITERIA_QUERY = "SELECT COUNT(*) FROM articles INNER JOIN users ON"
            + " articles.users_id = users.id WHERE articles.topic LIKE ? OR articles.text LIKE ? OR users.name LIKE ?;";

    public static final String HIDE_ARTICLE_QUERY = "UPDATE articles SET is_active = 0 WHERE id = ?;";

    public static final String RECOVER_ARTICLE_QUERY = "UPDATE articles SET is_active = 1 WHERE id = ?;";

    public static final String FIND_BY_ID_QUERY = "SELECT articles.*, users.name, users.photo_name FROM articles INNER JOIN"
            + " users ON users.id = articles.users_id WHERE articles.id = ? LIMIT 1;";

    public static final String FIND_BY_COMMENT_ID_QUERY = "SELECT articles.*, users.name, users.photo_name FROM articles"
            + " JOIN users ON users.id = articles.users_id JOIN comments ON comments.articles_id = articles.id WHERE"
            + " comments.id = ?;";

    public static final String FIND_BY_CRITERIA = "SELECT articles.*, users.name, users.photo_name FROM articles INNER JOIN users ON"
            + " articles.users_id = users.id WHERE articles.topic LIKE ? OR articles.text LIKE ? OR users.name LIKE ?"
            + " ORDER BY articles.id DESC LIMIT ?, ?;";

    public static final String FINDING_ARTICLES_QUERY = "SELECT articles.*, users.name, users.photo_name FROM articles"
            + " INNER JOIN users ON users.id = articles.users_id ORDER BY articles.id DESC LIMIT ?, ?;";

    public static final String FINDING_ACTIVE_ARTICLES_QUERY = "SELECT articles.*, users.name, users.photo_name FROM articles"
            + " INNER JOIN users ON users.id = articles.users_id WHERE articles.is_active = 1 AND"
            + " articles.is_blocked = 0 ORDER BY articles.id DESC LIMIT ?, ?;";

    private GlobalArticleQuery() {

    }
}
