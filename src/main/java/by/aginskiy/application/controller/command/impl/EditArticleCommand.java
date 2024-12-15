package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.service.impl.UserArticleServiceImpl;
import by.aginskiy.application.model.validator.QuestionDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to edit the article by the user.
 *
 * @author Dzmitry Ahinski
 */
public class EditArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Article currentArticle = (Article) session.getAttribute(SessionAttribute.CURRENT_ENTITY);
        String topic = (String) request.getAttribute(RequestParameter.ARTICLE_TOPIC);
        String text = (String) request.getAttribute(RequestParameter.ARTICLE_TEXT);
        CommandResult commandResult = null;
        try {
            if(QuestionDataValidator.isValidText(text) && QuestionDataValidator.isValidTopic(topic)) {
                UserArticleServiceImpl.INSTANCE.updateArticle(currentArticle.getId(), topic, text);
                commandResult = new CommandResult(UrlPath.USER_ARTICLE_LIST_DO, CommandResult.TransitionType.REDIRECT);
            } else {
                request.setAttribute(RequestParameter.INVALID_DATA, true);
                commandResult = new CommandResult(JspPath.EDIT_ARTICLE_PAGE, CommandResult.TransitionType.FORWARD);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while truing to update article, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
