package by.aginskiy.application.model.dao.query;

/**
 * Provides queries to work with article rating.
 *
 * @author Dzmitry Ahinski
 */
public class RatingQuery {

    public static final String SEARCH_RATING_BY_USER_QUERY = "SELECT rating.value FROM rating WHERE users_id = ? AND articles_id = ?;";

    public static final String IS_RATED_BY_USER_QUERY = "SELECT COUNT(1) FROM rating WHERE users_id = ? AND articles_id = ?;";

    public static final String ADD_RATING_QUERY = "INSERT INTO rating (value, users_id, articles_id) VALUES (?, ?, ?);";

    public static final String UPDATE_RATING_QUERY = "UPDATE rating SET value = ? WHERE users_id = ? AND articles_id = ?;";

    public static final String CALCULATE_POSITIVE_RATING_QUERY = "SELECT COUNT(*) FROM rating WHERE articles_id = ? AND value = 2;";

    public static final String CALCULATE_NEGATIVE_RATING_QUERY = "SELECT COUNT(*) FROM rating WHERE articles_id = ? AND value = 3;";

    private RatingQuery() {

    }
}
