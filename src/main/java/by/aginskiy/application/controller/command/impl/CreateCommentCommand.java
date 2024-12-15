package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.entity.Comment;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.CommentServiceImpl;
import by.aginskiy.application.model.validator.QuestionDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to create the comment by the user.
 *
 * @author Dzmitry Ahinski
 */
public class CreateCommentCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String commentText = (String) request.getAttribute(RequestParameter.COMMENT_TEXT);
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Article currentArticle = (Article) session.getAttribute(SessionAttribute.CURRENT_ENTITY);
        CommandResult commandResult = null;
        try {
            if(QuestionDataValidator.isValidText(commentText)) {
                Comment comment = new Comment(commentText, user.getName());
                CommentServiceImpl.INSTANCE.add(user.getId(), currentArticle.getId(), comment);
                commandResult = new CommandResult(UrlPath.VIEW_QUESTION_DO, CommandResult.TransitionType.REDIRECT);
            } else {
                commandResult = new CommandResult(UrlPath.VIEW_QUESTION_DO, CommandResult.TransitionType.FORWARD);
                request.setAttribute(RequestParameter.INVALID_DATA, true);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to add new comment, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
