package by.aginskiy.application.model.service.impl;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.dao.impl.*;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.service.UserArticleService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * UserArticleService implementation.
 *
 * @author Dzmitry Ahinski
 */
public enum UserArticleServiceImpl implements UserArticleService {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int calculateArticles(int userId) throws ServiceException {
        int articlesNumber = 0;
        try {
            articlesNumber = UserArticleDaoImpl.INSTANCE.calculateArticles(userId);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to calculate user articles, ", exception);
            throw new ServiceException(exception);
        }
        return articlesNumber;
    }

    @Override
    public void updateArticle(int articleId, String topic, String text) throws ServiceException {
        try {
            UserArticleDaoImpl.INSTANCE.updateArticle(articleId, topic, text);
        } catch (DaoException exception) {
            logger.log(Level.ERROR, "Error while trying to update article, ", exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<Article> findAll(int userId, int start, int limit) throws ServiceException {
        List<Article> articles = new ArrayList<>();
        try {
            articles = UserArticleDaoImpl.INSTANCE.findAll(userId, start, limit);
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
            logger.log(Level.ERROR, "Error while trying to find all user articles, to calculate their rating or"
                    + " to calculate article comments number, ", exception);
            throw new ServiceException(exception);
        }
        return articles;
    }
}
