package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.service.impl.CommentServiceImpl;
import by.aginskiy.application.model.validator.QuestionDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action command to edit the comment by the user.
 *
 * @author Dzmitry Ahinski
 */
public class EditCommentCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int commentId = Integer.parseInt(request.getParameter(RequestParameter.COMMENT_ID));
        String text = (String) request.getAttribute(RequestParameter.COMMENT_TEXT);
        CommandResult commandResult = null;
        try {
            if(QuestionDataValidator.isValidText(text)) {
                CommentServiceImpl.INSTANCE.updateCommentText(commentId, text);
                commandResult = new CommandResult(UrlPath.USER_COMMENT_LIST_DO, CommandResult.TransitionType.REDIRECT);
            } else {
                request.setAttribute(RequestParameter.INVALID_DATA, true);
                commandResult = new CommandResult(JspPath.EDIT_COMMENT_PAGE, CommandResult.TransitionType.FORWARD);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to update comment, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
