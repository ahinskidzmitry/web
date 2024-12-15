package by.aginskiy.application.model.dao;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.entity.Article;

import java.util.List;
import java.util.Optional;

/**
 * Interface used for interactions with articles table.
 *
 * @author Dzmitry Ahinski
 */
public interface GlobalArticleDao {

    /**
     * Adds article to the articles table.
     *
     * @param userId id of the user who add the article.
     * @param article article object to be added.
     * @throws DaoException if the database throws SQLException.
     */
    void add(int userId, Article article) throws DaoException;

    /**
     * Counts the number of articles that are not blocked or hidden.
     *
     * @return number of active articles.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateActiveArticles() throws DaoException;

    /**
     * Counts the number of all articles.
     *
     * @return number of articles.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateArticles() throws DaoException;

    /**
     * Counts the number of all articles by the search criteria.
     *
     * @param criteria search criteria as String object.
     * @return number of articles found by the criteria.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateByCriteria(String criteria) throws DaoException;

    /**
     * Checks if the article is blocked.
     *
     * @param articleId id of the article being checked.
     * @return true if the article is blocked, otherwise - false.
     * @throws DaoException if the database throws SQLException.
     */
    boolean isBlockedArticle(int articleId) throws DaoException;

    /**
     * Blocks the article.
     *
     * @param articleId id of the article to be blocked.
     * @throws DaoException if the database throws SQLException.
     */
    void blockArticle(int articleId) throws DaoException;

    /**
     * Unblocks the article.
     *
     * @param articleId id of the article to be unblocked.
     * @throws DaoException if the database throws SQLException.
     */
    void unblockArticle(int articleId) throws DaoException;

    /**
     * Hides the article from the global feed.
     *
     * @param articleId id of the article to hide.
     * @throws DaoException if the database throws SQLException.
     */
    void hideArticle(int articleId) throws DaoException;

    /**
     * Returns the article to the global feed.
     *
     * @param articleId id of the article to recover.
     * @throws DaoException if the database throws SQLException.
     */
    void recoverArticle(int articleId) throws DaoException;

    /**
     * Finds article by comment id.
     *
     * @param commentId id of the comment to search.
     * @return Optional with Article object if such article is exist, otherwise - empty optional.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Article> findByCommentId(int commentId) throws DaoException;

    /**
     * Finds article by id.
     *
     * @param articleId id of the article to search.
     * @return Optional with Article object if such article is exist, otherwise - empty optional.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Article> findById(int articleId) throws DaoException;

    /**
     * Finds all articles by the criteria.
     *
     * @param criteria search criteria as String object.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Article> findByCriteria(String criteria, int start, int limit) throws DaoException;

    /**
     * Finds all articles.
     *
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Article> findAll(int start, int limit) throws DaoException;

    /**
     * Finds all articles that are not blocked or hidden by the user.
     *
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Article> findAllActive(int start, int limit) throws DaoException;
}
