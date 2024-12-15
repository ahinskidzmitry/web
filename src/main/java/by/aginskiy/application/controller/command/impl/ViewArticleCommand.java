package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.entity.Comment;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.CommentServiceImpl;
import by.aginskiy.application.model.service.impl.GlobalArticleServiceImpl;
import by.aginskiy.application.tag.PaginationTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Action command to display the article.
 *
 * @author Dzmitry Ahinski
 */
public class ViewArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Article currentArticle = (Article) session.getAttribute(SessionAttribute.CURRENT_ENTITY);
        String articleIdStr = request.getParameter(RequestParameter.ARTICLE_ID);
        int articleId = 1;
        int currentPage = 1;
        if(articleIdStr != null)  {
            articleId = Integer.parseInt(articleIdStr);
        } else {
            articleId = currentArticle.getId();
            currentPage = (int) session.getAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE);
        }
        String requestedPage = request.getParameter(RequestParameter.REQUESTED_PAGE);
        if(requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * PaginationTag.PAGE_ENTITY_NUMBER;
        int limit = PaginationTag.PAGE_ENTITY_NUMBER;
        try {
            Optional<Article> articleOptional = GlobalArticleServiceImpl.INSTANCE.findById(articleId, user.getId());
            if(articleOptional.isPresent()) {
                Article article = articleOptional.get();
                session.setAttribute(SessionAttribute.CURRENT_ENTITY, article);
                List<Comment> comments = CommentServiceImpl.INSTANCE.findAllActive(articleId, user.getId(), start, limit);
                request.setAttribute(RequestParameter.CURRENT_ENTITY_LIST, comments);
                int commentsNumber = article.getCommentsNumber();
                request.setAttribute(RequestParameter.CURRENT_ENTITY_NUMBER, commentsNumber);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to find article or to find article comments", exception);
            throw new CommandException(exception);
        }
        CommandResult commandResult = new CommandResult(JspPath.ARTICLE_PAGE, CommandResult.TransitionType.FORWARD);
        return commandResult;
    }
}
