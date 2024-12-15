package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.service.impl.GlobalArticleServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action command to block the article by the administrator.
 *
 * @author Dzmitry Ahinski
 */
public class AdminBlockArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int articleId = Integer.parseInt(request.getParameter(RequestParameter.ARTICLE_ID));
        CommandResult commandResult = null;
        try {
            if(GlobalArticleServiceImpl.INSTANCE.isBlockedArticle(articleId)) {
                GlobalArticleServiceImpl.INSTANCE.unblockArticle(articleId);
            } else {
                GlobalArticleServiceImpl.INSTANCE.blockArticle(articleId);
            }
            commandResult = new CommandResult(UrlPath.ADMIN_VIEW_ARTICLE_DO, CommandResult.TransitionType.REDIRECT);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to block or to unblock article, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
