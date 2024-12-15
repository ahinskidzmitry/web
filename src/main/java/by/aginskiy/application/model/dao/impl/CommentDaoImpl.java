package by.aginskiy.application.model.dao.impl;

import by.aginskiy.application.exception.ConnectionPoolException;
import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.dao.CloseableDao;
import by.aginskiy.application.model.dao.ColumnName;
import by.aginskiy.application.model.dao.CommentDao;
import by.aginskiy.application.model.dao.query.*;
import by.aginskiy.application.model.entity.Comment;
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
 * CommentDao implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum CommentDaoImpl implements CommentDao, CloseableDao {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(int authorId, int articleId, Comment comment) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.ADDING_COMMENT_QUERY);
            statement.setString(1, comment.getText());
            statement.setInt(2, authorId);
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
    public boolean isLikedByUser(int userId, int commentId) throws DaoException {
        boolean isLiked = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.IS_LIKED_BY_USER_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, commentId);
            ResultSet resultSet = statement.executeQuery();
            isLiked = resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return isLiked;
    }

    @Override
    public void addLike(int userId, int commentId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.ADD_LIKE_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, commentId);
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
    public void deleteLike(int userId, int commentId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.REMOVE_LIKE_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, commentId);
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
    public void blockComment(int commentId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.BLOCK_COMMENT_QUERY);
            statement.setInt(1, commentId);
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
    public void unblockComment(int commentId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.UNBLOCK_COMMENT_QUERY);
            statement.setInt(1, commentId);
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
    public void hideComment(int commentId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.HIDE_COMMENT_QUERY);
            statement.setInt(1, commentId);
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
    public void recoverComment(int commentId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.RECOVER_COMMENT_QUERY);
            statement.setInt(1, commentId);
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
    public int calculateLikes(int commentId) throws DaoException {
        int likesNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.CALCULATE_LIKES_QUERY);
            statement.setInt(1, commentId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                likesNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return likesNumber;
    }

    @Override
    public int calculateActiveComments(int articleId) throws DaoException {
        int commentsNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.CALCULATE_ACTIVE_COMMENTS_QUERY);
            statement.setInt(1, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                commentsNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return commentsNumber;
    }

    @Override
    public int calculateComments(int articleId) throws DaoException {
        int commentsNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.CALCULATE_COMMENTS_QUERY);
            statement.setInt(1, articleId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                commentsNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return commentsNumber;
    }

    @Override
    public int calculateCommentsByUser(int userId) throws DaoException {
        int commentsNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.CALCULATE_USER_COMMENTS_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                commentsNumber = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return commentsNumber;
    }

    @Override
    public void updateCommentText(int commentId, String text) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.UPDATE_COMMENT_TEXT_QUERY);
            statement.setString(1, text);
            statement.setInt(2, commentId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public Optional<Comment> findById(int commentId) throws DaoException {
        Optional<Comment> commentOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.FIND_BY_ID_QUERY);
            statement.setInt(1, commentId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt(ColumnName.COMMENT_ID);
                String text = resultSet.getString(ColumnName.COMMENT_TEXT);
                String author = resultSet.getString(ColumnName.USER_NAME);
                LocalDateTime date = resultSet.getTimestamp(ColumnName.COMMENT_DATE).toLocalDateTime();
                String photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                boolean active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                boolean blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                Comment comment = new Comment(id, text, author, date, photoName, active, blocked);
                commentOptional = Optional.of(comment);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return commentOptional;
    }

    @Override
    public List<Comment> findAllByUser(int userId, int start, int limit) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.FINDING_USER_COMMENTS_QUERY);
            statement.setInt(1, userId);
            statement.setInt(2, start);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            String text = null;
            String author = null;
            LocalDateTime date = null;
            String photoName = null;
            boolean active = true;
            boolean blocked = false;
            Comment comment = null;
            while(resultSet.next()) {
                id = resultSet.getInt(ColumnName.COMMENT_ID);
                text = resultSet.getString(ColumnName.COMMENT_TEXT);
                author = resultSet.getString(ColumnName.USER_NAME);
                date = resultSet.getTimestamp(ColumnName.COMMENT_DATE).toLocalDateTime();
                photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                comment = new Comment(id, text, author, date, photoName, active, blocked);
                comments.add(comment);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return comments;
    }


    @Override
    public List<Comment> findAllActive(int articleId, int start, int limit) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.FINDING_ACTIVE_COMMENTS_QUERY);
            statement.setInt(1, articleId);
            statement.setInt(2, start);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            String text = null;
            String author = null;
            LocalDateTime date = null;
            String photoName = null;
            boolean active = true;
            boolean blocked = false;
            Comment comment = null;
            while(resultSet.next()) {
                id = resultSet.getInt(ColumnName.COMMENT_ID);
                text = resultSet.getString(ColumnName.COMMENT_TEXT);
                author = resultSet.getString(ColumnName.USER_NAME);
                date = resultSet.getTimestamp(ColumnName.COMMENT_DATE).toLocalDateTime();
                photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                comment = new Comment(id, text, author, date, photoName, active, blocked);
                comments.add(comment);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return comments;
    }

    @Override
    public List<Comment> findAll(int articleId, int start, int limit) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CommentQuery.FINDING_COMMENTS_QUERY);
            statement.setInt(1, articleId);
            statement.setInt(2, start);
            statement.setInt(3, limit);
            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            String text = null;
            String author = null;
            LocalDateTime date = null;
            String photoName = null;
            boolean active = true;
            boolean blocked = false;
            Comment comment = null;
            while(resultSet.next()) {
                id = resultSet.getInt(ColumnName.COMMENT_ID);
                text = resultSet.getString(ColumnName.COMMENT_TEXT);
                author = resultSet.getString(ColumnName.USER_NAME);
                date = resultSet.getTimestamp(ColumnName.COMMENT_DATE).toLocalDateTime();
                photoName = resultSet.getString(ColumnName.USER_PHOTO_NAME);
                active = resultSet.getBoolean(ColumnName.IS_ACTIVE);
                blocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
                comment = new Comment(id, text, author, date, photoName, active, blocked);
                comments.add(comment);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Error while trying to get connection or execute query, ", exception);
            throw new DaoException(exception);
        } finally {
            close(statement);
            close(connection);
        }
        return comments;
    }
}
