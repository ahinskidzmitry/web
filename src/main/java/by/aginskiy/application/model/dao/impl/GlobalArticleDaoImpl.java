package by.aginskiy.application.model.dao.impl;

import by.aginskiy.application.exception.ConnectionPoolException;
import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.dao.CloseableDao;
import by.aginskiy.application.model.dao.ColumnName;
import by.aginskiy.application.model.dao.GlobalArticleDao;
import by.aginskiy.application.model.dao.query.GlobalArticleQuery;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * GlobalArticleDao implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum GlobalArticleDaoImpl implements GlobalArticleDao, CloseableDao {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();
    private static final String PERCENT_SYMBOL = "%";

    @Override
    public void add(int userId, Article article) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.ADDING_ARTICLE_QUERY);
            statement.setString(1, article.getTopic());
            statement.setInt(2, userId);
            statement.setString(3, article.getText());
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
    public int calculateActiveArticles() throws DaoException {
        int articlesNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.CALCULATE_ACTIVE_ARTICLES_QUERY);
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
    public int calculateArticles() throws DaoException {
        int articlesNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.CALCULATE_ALL_ARTICLES_QUERY);
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
    public int calculateByCriteria(String criteria) throws DaoException {
        int articlesNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.CALCULATE_ARTICLES_BY_CRITERIA_QUERY);
            statement.setString(1, PERCENT_SYMBOL + criteria + PERCENT_SYMBOL);
            statement.setString(2, PERCENT_SYMBOL + criteria + PERCENT_SYMBOL);
            statement.setString(3, PERCENT_SYMBOL + criteria + PERCENT_SYMBOL);
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
    public boolean isBlockedArticle(int articleId) throws DaoException {
        boolean isBlocked = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.IS_BLOCKED_ARTICLE_QUERY);
            statement.setInt(1, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return isBlocked;
    }

    @Override
    public void blockArticle(int articleId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.BLOCK_ARTICLE_QUERY);
            statement.setInt(1, articleId);
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
    public void unblockArticle(int articleId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.UNBLOCK_ARTICLE_QUERY);
            statement.setInt(1, articleId);
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
    public void hideArticle(int articleId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.HIDE_ARTICLE_QUERY);
            statement.setInt(1, articleId);
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
    public void recoverArticle(int articleId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.RECOVER_ARTICLE_QUERY);
            statement.setInt(1, articleId);
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
    public Optional<Article> findByCommentId(int commentId) throws DaoException {
        Optional<Article> result = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.FIND_BY_COMMENT_ID_QUERY);
            statement.setInt(1, commentId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt(ColumnName.ARTICLE_ID);
                String topic = resultSet.getString(ColumnName.ARTICLE_TOPIC);
                String author = resultSet.getString(ColumnName.USER_NAME);
                String text = resultSet.getString(ColumnName.ARTICLE_TEXT);
                String photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                LocalDateTime date = resultSet.getTimestamp(ColumnName.ARTICLE_DATE).toLocalDateTime();
                boolean active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                boolean blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                Article article = new Article(id, topic, author, text, photoName, date, active, blocked);
                result = Optional.of(article);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Optional<Article> findById(int articleId) throws DaoException {
        Optional<Article> result = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.FIND_BY_ID_QUERY);
            statement.setInt(1, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt(ColumnName.ARTICLE_ID);
                String topic = resultSet.getString(ColumnName.ARTICLE_TOPIC);
                String author = resultSet.getString(ColumnName.USER_NAME);
                String text = resultSet.getString(ColumnName.ARTICLE_TEXT);
                String photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                LocalDateTime date = resultSet.getTimestamp(ColumnName.ARTICLE_DATE).toLocalDateTime();
                boolean active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                boolean blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                Article article = new Article(id, topic, author, text, photoName, date, active, blocked);
                result = Optional.of(article);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<Article> findByCriteria(String criteria, int start, int limit) throws DaoException {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.FIND_BY_CRITERIA);
            statement.setString(1, PERCENT_SYMBOL + criteria + PERCENT_SYMBOL);
            statement.setString(2, PERCENT_SYMBOL + criteria + PERCENT_SYMBOL);
            statement.setString(3, PERCENT_SYMBOL + criteria + PERCENT_SYMBOL);
            statement.setInt(4, start);
            statement.setInt(5, limit);
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

    @Override
    public List<Article> findAll(int start, int limit) throws DaoException {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.FINDING_ARTICLES_QUERY);
            statement.setInt(1, start);
            statement.setInt(2, limit);
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

    @Override
    public List<Article> findAllActive(int start, int limit) throws DaoException {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(GlobalArticleQuery.FINDING_ACTIVE_ARTICLES_QUERY);
            statement.setInt(1, start);
            statement.setInt(2, limit);
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
