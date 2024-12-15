package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.CommentServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to like the comment by the user.
 *
 * @author Dzmitry Ahinski
 */
public class LikeCommentCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        int commentId = Integer.parseInt(request.getParameter(RequestParameter.COMMENT_ID));
        CommandResult commandResult = null;
        try {
            if(CommentServiceImpl.INSTANCE.isLikedByUser(user.getId(), commentId)) {
                CommentServiceImpl.INSTANCE.deleteLike(user.getId(), commentId);
            } else {
                CommentServiceImpl.INSTANCE.addLike(user.getId(), commentId);
            }
            commandResult = new CommandResult(UrlPath.VIEW_QUESTION_DO, CommandResult.TransitionType.REDIRECT);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to check if comment liked by user, to delete or to"
                    + " add like, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
