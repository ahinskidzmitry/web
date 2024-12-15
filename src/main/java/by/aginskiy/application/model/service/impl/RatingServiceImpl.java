package by.aginskiy.application.model.service.impl;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.dao.impl.GlobalArticleDaoImpl;
import by.aginskiy.application.model.dao.impl.RatingDaoImpl;
import by.aginskiy.application.model.entity.Rating;
import by.aginskiy.application.model.service.RatingService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RatingService implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum RatingServiceImpl implements RatingService {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean isRatedByUser(int userId, int articleId) throws ServiceException {
        boolean isRated = false;
        try {
            isRated = RatingDaoImpl.INSTANCE.isRatedByUser(userId, articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to check if article is rated by user, ", exception);
            throw new ServiceException(exception);
        }
        return isRated;
    }

    @Override
    public Rating searchRatingByUser(int userId, int articleId) throws ServiceException {
        Rating ratingByUser = Rating.NOT_RATED;
        try {
            ratingByUser = RatingDaoImpl.INSTANCE.searchRatingByUser(userId, articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find rating by user, ", exception);
            throw new ServiceException(exception);
        }
        return ratingByUser;
    }

    @Override
    public void addRatingByUser(int userId, int articleId, Rating rating) throws ServiceException {
        try {
            RatingDaoImpl.INSTANCE.addRatingByUser(userId, articleId, rating);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to add rating by user, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void updateRatingByUser(int userId, int articleId, Rating rating) throws ServiceException {
        try {
            RatingDaoImpl.INSTANCE.updateRatingByUser(userId, articleId, rating);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to update rating by user, ", exception);
            throw new ServiceException(exception);
        }
    }
}
