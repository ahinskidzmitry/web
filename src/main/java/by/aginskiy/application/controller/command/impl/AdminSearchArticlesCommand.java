package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.controller.command.CommandType;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.service.impl.GlobalArticleServiceImpl;
import by.aginskiy.application.model.validator.SearchValidator;
import by.aginskiy.application.tag.PaginationTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command to search for articles by criteria by the administrator.
 *
 * @author Dzmitry Ahinski
 */
public class AdminSearchArticlesCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        int currentPage = 1;
        String criteria = (String) request.getAttribute(RequestParameter.CRITERIA);
        String requestedPage = request.getParameter(RequestParameter.REQUESTED_PAGE);
        if(requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * PaginationTag.PAGE_ENTITY_NUMBER;
        int limit = PaginationTag.PAGE_ENTITY_NUMBER;
        CommandResult commandResult = null;
        try {
            if(SearchValidator.isValidSearchText(criteria)) {
                List<Article> articles = GlobalArticleServiceImpl.INSTANCE.findByCriteria(criteria, start, limit);
                request.setAttribute(RequestParameter.CURRENT_ENTITY_LIST, articles);
                int articlesNumber = GlobalArticleServiceImpl.INSTANCE.calculateByCriteria(criteria);
                request.setAttribute(RequestParameter.CURRENT_ENTITY_NUMBER, articlesNumber);
                request.setAttribute(RequestParameter.CRITERIA, criteria);
                request.setAttribute(RequestParameter.COMMAND, CommandType.ADMIN_SEARCH_ARTICLES.name());
                commandResult = new CommandResult(JspPath.ARTICLES_LIST_PAGE, CommandResult.TransitionType.FORWARD);
            } else {
                commandResult = new CommandResult(UrlPath.ADMIN_ARTICLES_DO, CommandResult.TransitionType.REDIRECT);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to find articles by criteria or calculate their number, ",
                    exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
