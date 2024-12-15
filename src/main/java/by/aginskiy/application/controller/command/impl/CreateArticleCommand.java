package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.GlobalArticleServiceImpl;
import by.aginskiy.application.model.validator.QuestionDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to create the article by the user.
 *
 * @author Dzmitry Ahinski
 */
public class CreateArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String topic = (String) request.getAttribute(RequestParameter.ARTICLE_TOPIC);
        String text = (String) request.getAttribute(RequestParameter.ARTICLE_TEXT);
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Article article = new Article(topic, user.getName(), text);
        CommandResult commandResult = null;
        try {
            if(QuestionDataValidator.isValidTopic(topic) && QuestionDataValidator.isValidText(text)) {
                GlobalArticleServiceImpl.INSTANCE.add(user.getId(), article);
                commandResult = new CommandResult(UrlPath.GLOBAL_ARTICLE_LIST_DO, CommandResult.TransitionType.REDIRECT);
            } else {
                commandResult = new CommandResult(UrlPath.GLOBAL_ARTICLE_LIST_DO, CommandResult.TransitionType.FORWARD);
                request.setAttribute(RequestParameter.INVALID_DATA, true);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to add new article, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
