package by.aginskiy.application.model.service;

import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Rating;

/**
 * Interface provides actions on article's rating.
 *
 * @author Dzmitry Ahinski
 */
public interface RatingService {

    /**
     * Checks whether the user rated the article.
     *
     * @param userId id of the user being checked.
     * @param articleId id of the article being checked.
     * @return true if article is rated by user, otherwise - false.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean isRatedByUser(int userId, int articleId) throws ServiceException;

    /**
     * Finds the article rating by the user.
     *
     * @param userId id of the user to search.
     * @param articleId id of the article to search.
     * @return Rating object with the rating by user, otherwise - Rating object with NOT RATED value.
     * @throws ServiceException if an error occurs while processing.
     */
    Rating searchRatingByUser(int userId, int articleId) throws ServiceException;

    /**
     * Adds rating by the user to the rating table.
     *
     * @param userId id of the user who left the rating.
     * @param articleId id of the article that the rating belongs to.
     * @param rating rating object to be added.
     * @throws ServiceException if an error occurs while processing.
     */
    void addRatingByUser(int userId, int articleId, Rating rating) throws ServiceException;

    /**
     * Updates rating by the user in the rating table.
     *
     * @param userId id of the user who left the rating.
     * @param articleId id of the article that the rating belongs to.
     * @param rating rating object to be added.
     * @throws ServiceException if an error occurs while processing.
     */
    void updateRatingByUser(int userId, int articleId, Rating rating) throws ServiceException;
}
