package by.aginskiy.application.model.service.impl;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.dao.impl.CommentDaoImpl;
import by.aginskiy.application.model.dao.impl.GlobalArticleDaoImpl;
import by.aginskiy.application.model.dao.impl.RatingDaoImpl;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.entity.Rating;
import by.aginskiy.application.model.service.GlobalArticleService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * GlobalArticleService implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum GlobalArticleServiceImpl implements GlobalArticleService {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(int authorId, Article article) throws ServiceException {
        try {
            GlobalArticleDaoImpl.INSTANCE.add(authorId, article);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to add new article, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public int calculateActiveArticles() throws ServiceException {
        int articlesNumber = 0;
        try {
            articlesNumber = GlobalArticleDaoImpl.INSTANCE.calculateActiveArticles();
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate active articles, ", exception);
            throw new ServiceException(exception);
        }
        return articlesNumber;
    }

    @Override
    public int calculateArticles() throws ServiceException {
        int articlesNumber = 0;
        try {
            articlesNumber = GlobalArticleDaoImpl.INSTANCE.calculateArticles();
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate articles, ", exception);
            throw new ServiceException(exception);
        }
        return articlesNumber;
    }

    @Override
    public int calculateByCriteria(String criteria) throws ServiceException {
        int articlesNumber = 0;
        try {
            articlesNumber = GlobalArticleDaoImpl.INSTANCE.calculateByCriteria(criteria);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate articles by criteria, ", exception);
            throw new ServiceException(exception);
        }
        return articlesNumber;
    }

    @Override
    public boolean isBlockedArticle(int articleId) throws ServiceException {
        boolean isBlocked = false;
        try {
            isBlocked = GlobalArticleDaoImpl.INSTANCE.isBlockedArticle(articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to check if the article is blocked, ", exception);
            throw new ServiceException(exception);
        }
        return isBlocked;
    }

    @Override
    public void blockArticle(int articleId) throws ServiceException {
        try {
            GlobalArticleDaoImpl.INSTANCE.blockArticle(articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to block article, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void unblockArticle(int articleId) throws ServiceException {
        try {
            GlobalArticleDaoImpl.INSTANCE.unblockArticle(articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to unblock article, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void hideArticle(int articleId) throws ServiceException {
        try {
            GlobalArticleDaoImpl.INSTANCE.hideArticle(articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to hide article, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public void recoverArticle(int articleId) throws ServiceException {
        try {
            GlobalArticleDaoImpl.INSTANCE.recoverArticle(articleId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to recover article, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Optional<Article> findByCommentId(int commentId, int userId) throws ServiceException {
        Optional<Article> result = Optional.empty();
        try {
            result = GlobalArticleDaoImpl.INSTANCE.findByCommentId(commentId);
            if(result.isPresent()) {
                Article article = result.get();
                int positiveRating = RatingDaoImpl.INSTANCE.calculatePositiveRating(article.getId());
                int negativeRating = RatingDaoImpl.INSTANCE.calculateNegativeRating(article.getId());
                int commentsNumber = CommentDaoImpl.INSTANCE.calculateActiveComments(article.getId());
                Rating ratingByUser = RatingDaoImpl.INSTANCE.searchRatingByUser(userId, article.getId());
                article.setPositiveRating(positiveRating);
                article.setNegativeRating(negativeRating);
                article.setCommentsNumber(commentsNumber);
                article.setRatingByUser(ratingByUser);
                result = Optional.of(article);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find article by comment id, ", exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public Optional<Article> findById(int articleId) throws ServiceException {
        Optional<Article> result = Optional.empty();
        try {
            result = GlobalArticleDaoImpl.INSTANCE.findById(articleId);
            if(result.isPresent()) {
                Article article = result.get();
                int positiveRating = RatingDaoImpl.INSTANCE.calculatePositiveRating(article.getId());
                int negativeRating = RatingDaoImpl.INSTANCE.calculateNegativeRating(article.getId());
                int commentsNumber = CommentDaoImpl.INSTANCE.calculateActiveComments(article.getId());
                article.setPositiveRating(positiveRating);
                article.setNegativeRating(negativeRating);
                article.setCommentsNumber(commentsNumber);
                result = Optional.of(article);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find article by id, ", exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public Optional<Article> findById(int articleId, int userId) throws ServiceException {
        Optional<Article> result = Optional.empty();
        try {
            result = GlobalArticleDaoImpl.INSTANCE.findById(articleId);
            if(result.isPresent()) {
                Article article = result.get();
                int positiveRating = RatingDaoImpl.INSTANCE.calculatePositiveRating(article.getId());
                int negativeRating = RatingDaoImpl.INSTANCE.calculateNegativeRating(article.getId());
                int commentsNumber = CommentDaoImpl.INSTANCE.calculateActiveComments(article.getId());
                Rating ratingByUser = RatingDaoImpl.INSTANCE.searchRatingByUser(userId, article.getId());
                article.setPositiveRating(positiveRating);
                article.setNegativeRating(negativeRating);
                article.setCommentsNumber(commentsNumber);
                article.setRatingByUser(ratingByUser);
                result = Optional.of(article);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find user article by id, find rating by user or"
                    + " calculate comments number, ", exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public List<Article> findByCriteria(String criteria, int start, int limit) throws ServiceException {
        List<Article> articles = new ArrayList<>();
        try {
            articles = GlobalArticleDaoImpl.INSTANCE.findByCriteria(criteria, start, limit);
            int positiveRating = 0;
            int negativeRating = 0;
            int commentsNumber = 0;
            for(Article article : articles) {
                positiveRating = RatingDaoImpl.INSTANCE.calculatePositiveRating(article.getId());
                negativeRating = RatingDaoImpl.INSTANCE.calculateNegativeRating(article.getId());
                commentsNumber = CommentDaoImpl.INSTANCE.calculateActiveComments(article.getId());
                article.setPositiveRating(positiveRating);
                article.setNegativeRating(negativeRating);
                article.setCommentsNumber(commentsNumber);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find articles by criteria, find rating"
                    + " or calculate comments number, ", exception);
            throw new ServiceException(exception);
        }
        return articles;
    }

    @Override
    public List<Article> findAll(int start, int limit) throws ServiceException {
        List<Article> articles = new ArrayList<>();
        try {
            articles = GlobalArticleDaoImpl.INSTANCE.findAll(start, limit);
            int positiveRating = 0;
            int negativeRating = 0;
            int commentsNumber = 0;
            for(Article article : articles) {
                positiveRating = RatingDaoImpl.INSTANCE.calculatePositiveRating(article.getId());
                negativeRating = RatingDaoImpl.INSTANCE.calculateNegativeRating(article.getId());
                commentsNumber = CommentDaoImpl.INSTANCE.calculateComments(article.getId());
                article.setPositiveRating(positiveRating);
                article.setNegativeRating(negativeRating);
                article.setCommentsNumber(commentsNumber);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find articles or calculate their rating, ", exception);
            throw new ServiceException(exception);
        }
        return articles;
    }

    @Override
    public List<Article> findAllActive(int start, int limit) throws ServiceException {
        List<Article> articles = new ArrayList<>();
        try {
            articles = GlobalArticleDaoImpl.INSTANCE.findAllActive(start, limit);
            int positiveRating = 0;
            int negativeRating = 0;
            int commentsNumber = 0;
            for(Article article : articles) {
                positiveRating = RatingDaoImpl.INSTANCE.calculatePositiveRating(article.getId());
                negativeRating = RatingDaoImpl.INSTANCE.calculateNegativeRating(article.getId());
                commentsNumber = CommentDaoImpl.INSTANCE.calculateActiveComments(article.getId());
                article.setPositiveRating(positiveRating);
                article.setNegativeRating(negativeRating);
                article.setCommentsNumber(commentsNumber);
            }
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to find all user articles, calculate their rating"
                    + " or find rating by user, ", exception);
            throw new ServiceException(exception);
        }
        return articles;
    }
}
