package by.aginskiy.application.model.dao;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.entity.Rating;

/**
 * Interface used for interactions with rating table.
 *
 * @author Dzmitry Ahinski
 */
public interface RatingDao {

    /**
     * Checks whether the user rated the article.
     *
     * @param userId id of the user being checked.
     * @param articleId id of the article being checked.
     * @return true if article is rated by user, otherwise - false.
     * @throws DaoException if the database throws SQLException.
     */
    boolean isRatedByUser(int userId, int articleId) throws DaoException;

    /**
     * Finds the article rating by the user.
     *
     * @param userId id of the user to search.
     * @param articleId id of the article to search.
     * @return Rating object with the rating by user, otherwise - Rating object with NOT RATED value.
     * @throws DaoException if the database throws SQLException.
     */
    Rating searchRatingByUser(int userId, int articleId) throws DaoException;

    /**
     * Adds rating by the user to the rating table.
     *
     * @param userId id of the user who left the rating.
     * @param articleId id of the article that the rating belongs to.
     * @param rating rating object to be added.
     * @throws DaoException if the database throws SQLException.
     */
    void addRatingByUser(int userId, int articleId, Rating rating) throws DaoException;

    /**
     * Updates rating by the user in the rating table.
     *
     * @param userId id of the user who left the rating.
     * @param articleId id of the article that the rating belongs to.
     * @param rating rating object to be added.
     * @throws DaoException if the database throws SQLException.
     */
    void updateRatingByUser(int userId, int articleId, Rating rating) throws DaoException;

    /**
     * Counts the number of positive rating of the article.
     *
     * @param articleId id of the article for counting rating.
     * @return number of positive rating.
     * @throws DaoException if the database throws SQLException.
     */
    int calculatePositiveRating(int articleId) throws DaoException;

    /**
     * Counts the number of negative rating of the article.
     *
     * @param articleId id of the article for counting rating.
     * @return number of negative rating.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateNegativeRating(int articleId) throws DaoException;
}
