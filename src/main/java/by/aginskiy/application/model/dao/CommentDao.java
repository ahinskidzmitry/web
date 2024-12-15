package by.aginskiy.application.model.dao;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.entity.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Interface used for interactions with comments table.
 *
 * @author Dzmitry Ahinski
 */
public interface CommentDao {

    /**
     * Adds comment to the table.
     *
     * @param authorId id of the user who left the comment.
     * @param articleId id of the article that the comment belongs to.
     * @param comment comment object to be added.
     * @throws DaoException if the database throws SQLException.
     */
    void add(int authorId, int articleId, Comment comment) throws DaoException;

    /**
     * Checks whether the user rated the comment.
     *
     * @param userId id of the user being checked.
     * @param commentId id of the comment being checked.
     * @return true if comment is liked by user, otherwise - false.
     * @throws DaoException if the database throws SQLException.
     */
    boolean isLikedByUser(int userId, int commentId) throws DaoException;

    /**
     * Adds like to the comment.
     *
     * @param userId id of the user who left the like.
     * @param commentId id of the comment to be liked.
     * @throws DaoException if the database throws SQLException.
     */
    void addLike(int userId, int commentId) throws DaoException;

    /**
     * Deletes the like from a comment.
     *
     * @param userId id of the user who deletes the like.
     * @param commentId id of the comment that the like is being removed from.
     * @throws DaoException if the database throws SQLException.
     */
    void deleteLike(int userId, int commentId) throws DaoException;

    /**
     * Blocks the comment.
     *
     * @param commentId id of the comment to be blocked.
     * @throws DaoException if the database throws SQLException.
     */
    void blockComment(int commentId) throws DaoException;

    /**
     * Unblocks the comment.
     *
     * @param commentId id of the comment to be unblocked.
     * @throws DaoException if the database throws SQLException.
     */
    void unblockComment(int commentId) throws DaoException;

    /**
     * Hides the comment from global feed.
     *
     * @param commentId id of the comment to hide.
     * @throws DaoException if the database throws SQLException.
     */
    void hideComment(int commentId) throws DaoException;

    /**
     * Returns a comment to the global feed.
     *
     * @param commentId id of the comment to recover.
     * @throws DaoException if the database throws SQLException.
     */
    void recoverComment(int commentId) throws DaoException;

    /**
     * Calculates comment's likes.
     *
     * @param commentId id of the comment for counting likes.
     * @return number of likes for a comment
     * @throws DaoException if the database throws SQLException.
     */
    int calculateLikes(int commentId) throws DaoException;

    /**
     * Counts the number of comments that are not blocked or hidden by the user, belonging to the article.
     *
     * @param articleId id of the article for counting comments.
     * @return number of active comments.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateActiveComments(int articleId) throws DaoException;

    /**
     * Counts the number of all comments belonging to the article.
     *
     * @param articleId id of the article for counting comments.
     * @return number of comments.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateComments(int articleId) throws DaoException;

    /**
     * Counts the number of all comments belonging to the user.
     *
     * @param userId id of the user for counting comments.
     * @return number of comments.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateCommentsByUser(int userId) throws DaoException;

    /**
     * Updates comment's text.
     *
     * @param commentId id of the comment to be updated.
     * @param text new comment text.
     * @throws DaoException if the database throws SQLException.
     */
    void updateCommentText(int commentId, String text) throws DaoException;

    /**
     * Finds comment by comment id.
     *
     * @param commentId id of the comment to search.
     * @return Optional with Comment object if such comment is exist, otherwise - empty optional.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Comment> findById(int commentId) throws DaoException;

    /**
     * Finds all user comments.
     *
     * @param userId id of the user to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found comments, if the comments are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Comment> findAllByUser(int userId, int start, int limit) throws DaoException;

    /**
     * Finds all comments that are not blocked or hidden by the user, belonging to the article.
     *
     * @param articleId of the article to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found comments, if the comments are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Comment> findAllActive(int articleId, int start, int limit) throws DaoException;

    /**
     * Finds all comments belonging to the article.
     *
     * @param articleId of the article to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found comments, if the comments are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Comment> findAll(int articleId, int start, int limit) throws DaoException;
}
