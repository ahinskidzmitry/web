package by.aginskiy.application.model.service;

import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;

import java.util.List;

/**
 * Interface provides actions on user articles.
 *
 * @author Dzmitry Ahinski
 */
public interface UserArticleService {

    /**
     * Counts the number of all user articles.
     *
     * @param userId id of the user to search.
     * @return number of user articles.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateArticles(int userId) throws ServiceException;

    /**
     * Updates the article by the user.
     *
     * @param articleId id of the article to update.
     * @param topic new article topic to update
     * @param text new article text to update
     * @throws ServiceException if an error occurs while processing.
     */
    void updateArticle(int articleId, String topic, String text) throws ServiceException;

    /**
     * Finds all user articles.
     *
     * @param userId id of the user to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Article> findAll(int userId, int start, int limit) throws ServiceException;
}
