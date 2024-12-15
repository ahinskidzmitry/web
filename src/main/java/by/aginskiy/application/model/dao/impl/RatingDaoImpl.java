package by.aginskiy.application.model.dao.impl;

import by.aginskiy.application.exception.ConnectionPoolException;
import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.dao.CloseableDao;
import by.aginskiy.application.model.dao.ColumnName;
import by.aginskiy.application.model.dao.RatingDao;
import by.aginskiy.application.model.dao.query.RatingQuery;
import by.aginskiy.application.model.entity.Rating;
import by.aginskiy.application.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RatingDao implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum RatingDaoImpl implements RatingDao, CloseableDao {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean isRatedByUser(int userId, int articleId) throws DaoException {
        boolean isRated = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(RatingQuery.IS_RATED_BY_USER_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, articleId);
            ResultSet resultSet = statement.executeQuery();
            isRated = resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return isRated;
    }


    @Override
    public Rating searchRatingByUser(int userId, int articleId) throws DaoException {
        Rating rating = Rating.NOT_RATED;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(RatingQuery.SEARCH_RATING_BY_USER_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                rating = Rating.valueOf(resultSet.getString(ColumnName.LIKE_VALUE));
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return rating;
    }

    @Override
    public void addRatingByUser(int userId, int articleId, Rating rating) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(RatingQuery.ADD_RATING_QUERY);
            statement.setInt(1, rating.ordinal() + 1);
            statement.setInt(2, userId);
            statement.setInt(3, articleId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void updateRatingByUser(int userId, int articleId, Rating rating) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(RatingQuery.UPDATE_RATING_QUERY);
            statement.setInt(1, rating.ordinal() + 1);
            statement.setInt(2, userId);
            statement.setInt(3, articleId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public int calculatePositiveRating(int articleId) throws DaoException {
        int articleRating = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(RatingQuery.CALCULATE_POSITIVE_RATING_QUERY);
            statement.setInt(1, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                articleRating = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return articleRating;
    }

    @Override
    public int calculateNegativeRating(int articleId) throws DaoException {
        int articleRating = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(RatingQuery.CALCULATE_NEGATIVE_RATING_QUERY);
            statement.setInt(1, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                articleRating = -resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return articleRating;
    }
}
