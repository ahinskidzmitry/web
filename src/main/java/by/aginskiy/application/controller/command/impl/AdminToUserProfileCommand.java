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
import by.aginskiy.application.model.service.impl.UserArticleServiceImpl;
import by.aginskiy.application.model.service.impl.UserServiceImpl;
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
 * Action command to switch to a user profile.
 *
 * @author Dzmitry Ahinski
 */
public class AdminToUserProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String userIdStr = request.getParameter(RequestParameter.USER_ID);
        int userId = 0;
        if(userIdStr != null) {
            userId = Integer.parseInt(userIdStr);
        } else {
            User user = (User) session.getAttribute(SessionAttribute.CURRENT_ENTITY);
            userId = user.getId();
        }
        int currentPage = 1;
        String requestedPage = request.getParameter(RequestParameter.REQUESTED_PAGE);
        if(requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * PaginationTag.PAGE_ENTITY_NUMBER;
        int limit = PaginationTag.PAGE_ENTITY_NUMBER;
        try {
            Optional<User> userOptional = UserServiceImpl.INSTANCE.findById(userId);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                session.setAttribute(SessionAttribute.CURRENT_ENTITY, user);
                List<Article> articleList = UserArticleServiceImpl.INSTANCE.findAll(user.getId(), start, limit);
                request.setAttribute(RequestParameter.CURRENT_ENTITY_LIST, articleList);
                int articlesNumber = UserArticleServiceImpl.INSTANCE.calculateArticles(user.getId());
                request.setAttribute(RequestParameter.CURRENT_ENTITY_NUMBER, articlesNumber);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to find user by id, to find user articles or to"
                    + " calculate their number, ", exception);
            throw new CommandException(exception);
        }
        return new CommandResult(JspPath.ADMIN_USER_PAGE, CommandResult.TransitionType.FORWARD);
    }
}
