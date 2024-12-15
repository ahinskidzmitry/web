package by.aginskiy.application.model.dao;

import by.aginskiy.application.exception.DaoException;
import by.aginskiy.application.model.entity.Article;

import java.util.List;

public interface UserArticleDao {

    /**
     * Counts the number of all user articles.
     *
     * @param userId id of the user to search.
     * @return number of user articles.
     * @throws DaoException if the database throws SQLException.
     */
    int calculateArticles(int userId) throws DaoException;

    /**
     * Updates the article by the user.
     *
     * @param articleId id of the article to update.
     * @param topic new article topic to update
     * @param text new article text to update
     * @throws DaoException if the database throws SQLException.
     */
    void updateArticle(int articleId, String topic, String text) throws DaoException;

    /**
     * Finds all user articles.
     *
     * @param authorId id of the user to search.
     * @param start starting point of the search.
     * @param limit number of entities to be found.
     * @return List with the found articles, if the articles are not found - an empty list.
     * @throws DaoException if the database throws SQLException.
     */
    List<Article> findAll(int authorId, int start, int limit) throws DaoException;
}
