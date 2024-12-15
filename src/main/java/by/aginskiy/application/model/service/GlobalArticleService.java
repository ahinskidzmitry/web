package by.aginskiy.application.model.service;

import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on global articles.
 *
 * @author Dzmitry Ahinski
 */
public interface GlobalArticleService {

    /**
     * Adds article to the articles table.
     *
     * @param authorId id of the user who add the article.
     * @param article article object to be added.
     * @throws ServiceException if an error occurs while processing.
     */
    void add(int authorId, Article article) throws ServiceException;

    /**
     * Counts the number of articles that are not blocked or hidden.
     *
     * @return number of active articles.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateActiveArticles() throws ServiceException;

    /**
     * Counts the number of all articles.
     *
     * @return number of articles.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateArticles() throws ServiceException;

    /**
     * Counts the number of all articles by the search criteria.
     *
     * @param criteria search criteria as String object.
     * @return number of articles found by the criteria.
     * @throws ServiceException if an error occurs while processing.
     */
    int calculateByCriteria(String criteria) throws ServiceException;

    /**
     * Checks if the article is blocked.
     *
     * @param articleId id of the article being checked.
     * @return true if the article is blocked, otherwise - false.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean isBlockedArticle(int articleId) throws ServiceException;

    /**
     * Blocks the article.
     *
     * @param articleId id of the article to be blocked.
     * @throws ServiceException if an error occurs while processing.
     */
    void blockArticle(int articleId) throws ServiceException;

    /**
     * Unblocks the article.
     *
     * @param articleId id of the article to be unblocked.
     * @throws ServiceException if an error occurs while processing.
     */
    void unblockArticle(int articleId) throws ServiceException;

    /**
     * Hides the article from the global feed.
     *
     * @param articleId id of the article to hide.
     * @throws ServiceException if an error occurs while processing.
     */
    void hideArticle(int articleId) throws ServiceException;

    /**
     * Returns the article to the global feed.
     *
     * @param articleId id of the article to recover.
     * @throws ServiceException if an error occurs while processing.
     */
    void recoverArticle(int articleId) throws ServiceException;

    /**
     * Finds article by comment id.
     *
     * @param commentId id of the comment to search.
     * @param userId id of the user to find rating.
     * @return Optional with Article object if such article is exist, otherwise - empty optional.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Article> findByCommentId(int commentId, int userId) throws ServiceException;

    /**
     * Finds article by id.
     *
     * @param articleId id of the article to search.
     * @return Optional with Article object if such article is exist, otherwise - empty optional.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Article> findById(int articleId) throws ServiceException;

    /**
     * Finds article by id with user rating.
     *
     * @param articleId id of the article to search.
     * @param userId id of the user to find rating.
     * @return Optional with Article object if such article is exist, otherwise - empty optional.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Article> findById(int articleId, int userId) throws ServiceException;

    /**
     * Finds all articles by the criteria.
     *
     * @param criteria search criteria as String object.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Article> findByCriteria(String criteria, int start, int limit) throws ServiceException;

    /**
     * Finds all articles.
     *
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Article> findAll(int start, int limit) throws ServiceException;

    /**
     * Finds all articles that are not blocked or hidden by the user.
     *
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Article> findAllActive(int start, int limit) throws ServiceException;
}
