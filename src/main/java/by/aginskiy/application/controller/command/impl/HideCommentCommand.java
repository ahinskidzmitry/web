package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.service.impl.CommentServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action command to hide the comment by the user.
 *
 * @author Dzmitry Ahinski
 */
public class HideCommentCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final int START_PAGE = 1;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int commentId = Integer.parseInt(request.getParameter(RequestParameter.COMMENT_ID));
        CommandResult commandResult = null;
        try {
            CommentServiceImpl.INSTANCE.hideComment(commentId);
            commandResult = new CommandResult(UrlPath.USER_COMMENT_LIST_DO, CommandResult.TransitionType.REDIRECT);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to hide comment, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
