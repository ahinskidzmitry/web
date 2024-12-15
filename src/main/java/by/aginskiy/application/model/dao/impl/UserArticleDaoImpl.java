package by.aginskiy.application.model.dao.impl;

import by.aginskiy.application.exception.ConnectionPoolException;
import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.dao.UserArticleDao;
import by.aginskiy.application.model.dao.CloseableDao;
import by.aginskiy.application.model.dao.ColumnName;
import by.aginskiy.application.model.dao.query.UserArticleQuery;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UserArticleDao implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum UserArticleDaoImpl implements UserArticleDao, CloseableDao {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int calculateArticles(int userId) throws DaoException {
        int articlesNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserArticleQuery.CALCULATE_ARTICLES_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                articlesNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return articlesNumber;
    }

    @Override
    public void updateArticle(int articleId, String topic, String text) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserArticleQuery.UPDATE_ARTICLE_QUERY);
            statement.setString(1, topic);
            statement.setString(2, text);
            statement.setInt(3, articleId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Article> findAll(int authorId, int start, int limit) throws DaoException {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UserArticleQuery.FINDING_ARTICLE_QUERY);
            statement.setInt(1, authorId);
            statement.setInt(2, start);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            String topic = null;
            String author = null;
            String text = null;
            String photoName = null;
            LocalDateTime date = null;
            boolean active = true;
            boolean blocked = false;
            Article article = null;
            while(resultSet.next()) {
                id = resultSet.getInt(ColumnName.ARTICLE_ID);
                topic = resultSet.getString(ColumnName.ARTICLE_TOPIC);
                author = resultSet.getString(ColumnName.USER_NAME);
                text = resultSet.getString(ColumnName.ARTICLE_TEXT);
                photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                date = resultSet.getTimestamp(ColumnName.ARTICLE_DATE).toLocalDateTime();
                active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                article = new Article(id, topic, author, text, photoName, date, active, blocked);
                articles.add(article);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return articles;
    }
}
