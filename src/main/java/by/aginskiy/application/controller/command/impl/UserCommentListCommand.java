package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Comment;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.CommentServiceImpl;
import by.aginskiy.application.tag.PaginationTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command to display the user comments list.
 *
 * @author Dzmitry Ahinski
 */
public class UserCommentListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
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
            List<Comment> comments = CommentServiceImpl.INSTANCE.findAllByUser(user.getId(), start, limit);
            request.setAttribute(RequestParameter.CURRENT_ENTITY_LIST, comments);
            int commentsNumber = CommentServiceImpl.INSTANCE.calculateCommentsByUser(user.getId());
            request.setAttribute(RequestParameter.CURRENT_ENTITY_NUMBER, commentsNumber);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying find or calculate comments by user, ",
                    exception);
            throw new CommandException(exception);
        }
        return new CommandResult(JspPath.COMMENTS_LIST_PAGE, CommandResult.TransitionType.FORWARD);
    }
}
