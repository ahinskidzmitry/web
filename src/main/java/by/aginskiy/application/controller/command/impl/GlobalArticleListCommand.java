package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.GlobalArticleServiceImpl;
import by.aginskiy.application.tag.PaginationTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command to display global article list to the user.
 *
 * @author Dzmitry Ahinski
 */
public class GlobalArticleListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Integer currentPage = (Integer) session.getAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE);
        String requestedPage = request.getParameter(RequestParameter.REQUESTED_PAGE);
        if(requestedPage == null && currentPage == null) {
            currentPage = 1;
        } else if(requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * PaginationTag.PAGE_ENTITY_NUMBER;
        int limit = PaginationTag.PAGE_ENTITY_NUMBER;
        try {
            List<Article> articleList = GlobalArticleServiceImpl.INSTANCE.findAllActive(start, limit);
            request.setAttribute(RequestParameter.CURRENT_ENTITY_LIST, articleList);
            int articlesNumber = GlobalArticleServiceImpl.INSTANCE.calculateActiveArticles();
            request.setAttribute(RequestParameter.CURRENT_ENTITY_NUMBER, articlesNumber);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to find active articles or to calculate their number, ",
                    exception);
            throw new CommandException(exception);
        }
        return new CommandResult(JspPath.FEED_PAGE, CommandResult.TransitionType.FORWARD);
    }
}
