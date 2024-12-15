package by.aginskiy.application.model.service.impl;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.dao.impl.CommentDaoImpl;
import by.aginskiy.application.model.entity.Comment;
import by.aginskiy.application.model.service.CommentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CommentService implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum CommentServiceImpl implements CommentService {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(int authorId, int articleId, Comment comment) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.add(authorId, articleId, comment);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to add a comment, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public boolean isLikedByUser(int userId, int commentId) throws ServiceException {
        boolean isLiked = false;
        try {
            isLiked = CommentDaoImpl.INSTANCE.isLikedByUser(userId, commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to check user like, ", exception);
            throw new ServiceException(exception);
        }
        return isLiked;
    }

    @Override
    public void addLike(int userId, int commentId) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.addLike(userId, commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to add like by user, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void deleteLike(int userId, int commentId) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.deleteLike(userId, commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to remove like by user, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public int calculateComments(int articleId) throws ServiceException {
        int commentsNumber = 0;
        try {
            commentsNumber = CommentDaoImpl.INSTANCE.calculateComments(articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate comments", exception);
            throw new ServiceException(exception);
        }
        return commentsNumber;
    }

    @Override
    public int calculateCommentsByUser(int userId) throws ServiceException {
        int commentsNumber = 0;
        try {
            commentsNumber = CommentDaoImpl.INSTANCE.calculateCommentsByUser(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate comments by user, ", exception);
            throw new ServiceException(exception);
        }
        return commentsNumber;
    }

    @Override
    public void blockComment(int commentId) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.blockComment(commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to block comment, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void unblockComment(int commentId) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.unblockComment(commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to unblock comment, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void hideComment(int commentId) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.hideComment(commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to hide comment, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void recoverComment(int commentId) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.recoverComment(commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to recover comment, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void updateCommentText(int commentId, String text) throws ServiceException {
        try {
            CommentDaoImpl.INSTANCE.updateCommentText(commentId, text);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to update comment text, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Optional<Comment> findById(int commentId) throws ServiceException {
        Optional<Comment> commentOptional = Optional.empty();
        try {
            commentOptional = CommentDaoImpl.INSTANCE.findById(commentId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find comment by id, ", exception);
            throw new ServiceException(exception);
        }
        return commentOptional;
    }

    @Override
    public List<Comment> findAllByUser(int userId, int start, int limit) throws ServiceException {
        List<Comment> comments = new ArrayList<>();
        try {
            comments = CommentDaoImpl.INSTANCE.findAllByUser(userId, start, limit);
            int likesNumber = 0;
            for(Comment comment : comments) {
                likesNumber = CommentDaoImpl.INSTANCE.calculateLikes(comment.getId());
                comment.setLikesNumber(likesNumber);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find comments by user or to calculate comment"
                    + " likes, ", exception);
            throw new ServiceException(exception);
        }
        return comments;
    }

    @Override
    public List<Comment> findAll(int articleId, int start, int limit) throws ServiceException {
        List<Comment> comments = new ArrayList<>();
        try {
            comments = CommentDaoImpl.INSTANCE.findAll(articleId, start, limit);
            int likesNumber = 0;
            for(Comment comment : comments) {
                likesNumber = CommentDaoImpl.INSTANCE.calculateLikes(comment.getId());
                comment.setLikesNumber(likesNumber);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find comments or to calculate comment"
                    + " likes, ", exception);
            throw new ServiceException(exception);
        }
        return comments;
    }

    @Override
    public List<Comment> findAllActive(int articleId, int userId, int start, int limit) throws ServiceException {
        List<Comment> comments = new ArrayList<>();
        try {
            comments = CommentDaoImpl.INSTANCE.findAllActive(articleId, start, limit);
            int likesNumber = 0;
            boolean likedByUser = false;
            for(Comment comment : comments) {
                likesNumber = CommentDaoImpl.INSTANCE.calculateLikes(comment.getId());
                likedByUser = CommentDaoImpl.INSTANCE.isLikedByUser(userId, comment.getId());
                comment.setLikesNumber(likesNumber);
                comment.setLikedByUser(likedByUser);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find comments, check if comment is liked by user,"
                    + " or to calculate comment likes, ", exception);
            throw new ServiceException(exception);
        }
        return comments;
    }
}
