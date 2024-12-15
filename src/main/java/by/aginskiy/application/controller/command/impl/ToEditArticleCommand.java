package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Article;
import by.aginskiy.application.model.service.impl.GlobalArticleServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Action command to go to the edit article page.
 *
 * @author Dzmitry Ahinski
 */
public class ToEditArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        int articleId = Integer.parseInt(request.getParameter(RequestParameter.ARTICLE_ID));
        Optional<Article> article = Optional.empty();
        try {
            article = GlobalArticleServiceImpl.INSTANCE.findById(articleId);
            if(article.isPresent()) {
                session.setAttribute(SessionAttribute.CURRENT_ENTITY, article.get());
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to find article by id, " + articleId);
            throw new CommandException(exception);
        }
        CommandResult commandResult = new CommandResult(JspPath.EDIT_ARTICLE_PAGE, CommandResult.TransitionType.REDIRECT);
        logger.log(Level.DEBUG, "Go to edit article page");
        return commandResult;
    }
}
