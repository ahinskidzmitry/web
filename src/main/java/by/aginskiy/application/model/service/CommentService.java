package by.aginskiy.application.model.service;

import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on comments.
 *
 * @author Dzmitry Ahinski
 */
public interface CommentService {

    /**
     * Adds new comment by the user.
     *
     * @param authorId id of the user who left the comment.
     * @param articleId id of the article that the comment belongs to.
     * @param comment comment object to be added.
     * @throws ServiceException if an error occurs while processing.
     */
    void add(int authorId, int articleId, Comment comment) throws ServiceException;

    /**
     * Checks whether the user rated the comment.
     *
     * @param userId id of the user being checked.
     * @param commentId id of the comment being checked.
     * @return true if comment is liked by user, otherwise - false.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean isLikedByUser(int userId, int commentId) throws ServiceException;

    /**
     * Adds like to the comment.
     *
     * @param userId id of the user who left the like.
     * @param commentId id of the comment to be liked.
     * @throws ServiceException if an error occurs while processing.
     */
    void addLike(int userId, int commentId) throws ServiceException;

    /**
     * Deletes the like from a comment.
     *
     * @param userId id of the user who deletes the like.
     * @param commentId id of the comment that the like is being removed from.
     * @throws ServiceException if an error occurs while processing.
     */
    void deleteLike(int userId, int commentId) throws ServiceException;

    /**
     * Counts the number of all comments belonging to the article.
     *
     * @param articleId id of the article for counting comments.
     * @return number of comments.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateComments(int articleId) throws ServiceException;

    /**
     * Counts the number of all comments belonging to the user.
     *
     * @param userId id of the user for counting comments.
     * @return number of comments.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateCommentsByUser(int userId) throws ServiceException;

    /**
     * Blocks the comment.
     *
     * @param commentId id of the comment to be blocked.
     * @throws ServiceException if an error occurs while processing.
     */
    void blockComment(int commentId) throws ServiceException;

    /**
     * Unblocks the comment.
     *
     * @param commentId id of the comment to be unblocked.
     * @throws ServiceException if an error occurs while processing.
     */
    void unblockComment(int commentId) throws ServiceException;

    /**
     * Hides the comment from global feed.
     *
     * @param commentId id of the comment to hide.
     * @throws ServiceException if an error occurs while processing.
     */
    void hideComment(int commentId) throws ServiceException;

    /**
     * Returns a comment to the global feed.
     *
     * @param commentId id of the comment to recover.
     * @throws ServiceException if an error occurs while processing.
     */
    void recoverComment(int commentId) throws ServiceException;

    /**
     * Updates comment's text.
     *
     * @param commentId id of the comment to be updated.
     * @param text new comment text.
     * @throws ServiceException if an error occurs while processing.
     */
    void updateCommentText(int commentId, String text) throws ServiceException;

    /**
     * Finds comment by comment id.
     *
     * @param commentId id of the comment to search.
     * @return Optional with Comment object if such comment is exist, otherwise - empty optional.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Comment> findById(int commentId) throws ServiceException;

    /**
     * Finds all user comments.
     *
     * @param userId id of the user to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found comments, if the comments are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Comment> findAllByUser(int userId, int start, int limit) throws ServiceException;

    /**
     * Finds all comments belonging to the article.
     *
     * @param articleId of the article to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found comments, if the comments are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Comment> findAll(int articleId, int start, int limit) throws ServiceException;

    /**
     * Finds all comments that are not blocked or hidden by the user, belonging to the article.
     *
     * @param articleId id of the article to search.
     * @param userId id of the user to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found comments, if the comments are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Comment> findAllActive(int articleId, int userId, int start, int limit) throws ServiceException;
}
