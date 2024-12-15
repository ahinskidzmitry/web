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
 * Action command to display the article by comment.
 *
 * @author Dzmitry Ahinski
 */
public class ViewCommentArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        int commentId = Integer.parseInt(request.getParameter(RequestParameter.COMMENT_ID));
        session.setAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE, PaginationTag.PAGINATION_FIRST_PAGE);
        int start = (PaginationTag.PAGINATION_FIRST_PAGE - 1) * PaginationTag.PAGE_ENTITY_NUMBER;
        int limit = PaginationTag.PAGE_ENTITY_NUMBER;
        try {
            Optional<Article> articleOptional = GlobalArticleServiceImpl.INSTANCE.findByCommentId(commentId, user.getId());
            if(articleOptional.isPresent()) {
                Article article = articleOptional.get();
                session.setAttribute(SessionAttribute.CURRENT_ENTITY, article);
                List<Comment> comments = CommentServiceImpl.INSTANCE.findAllActive(article.getId(), user.getId(), start, limit);
                request.setAttribute(RequestParameter.CURRENT_ENTITY_LIST, comments);
                int commentsNumber = article.getCommentsNumber();
                request.setAttribute(RequestParameter.CURRENT_ENTITY_NUMBER, commentsNumber);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to find article by commentId or to find article"
                    + " comments, ", exception);
            throw new CommandException(exception);
        }
        CommandResult commandResult = new CommandResult(JspPath.ARTICLE_PAGE, CommandResult.TransitionType.FORWARD);
        return commandResult;
    }
}
