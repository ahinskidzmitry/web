package by.aginskiy.application.model.dao.query;

/**
 * Provides queries to work with user articles.
 *
 * @author Dzmitry Ahinski
 */
public class UserArticleQuery {

    public static final String CALCULATE_ARTICLES_QUERY = "SELECT COUNT(*) FROM articles WHERE users_id = ?;";

    public static final String UPDATE_ARTICLE_QUERY = "UPDATE articles SET topic = ?, text = ? WHERE id = ?";

    public static final String FINDING_ARTICLE_QUERY = "SELECT articles.*, users.name, users.photo_name FROM articles"
            + " INNER JOIN users ON users.id = articles.users_id WHERE articles.users_id = ? ORDER BY articles.id"
            + " DESC LIMIT ?, ?;";

    private UserArticleQuery() {

    }
}
