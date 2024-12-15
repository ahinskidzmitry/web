package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.Rating;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.RatingServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to dislike the article by the user.
 *
 * @author Dzmitry Ahinski
 */
public class DislikeArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        int articleId = Integer.parseInt(request.getParameter(RequestParameter.ARTICLE_ID));
        CommandResult commandResult = null;
        try {
            if(RatingServiceImpl.INSTANCE.isRatedByUser(user.getId(), articleId)) {
                Rating rating = RatingServiceImpl.INSTANCE.searchRatingByUser(user.getId(), articleId);
                switch (rating) {
                    case DISLIKED: {
                        RatingServiceImpl.INSTANCE.updateRatingByUser(user.getId(), articleId,
                                Rating.NOT_RATED);
                        break;
                    } default: {
                        RatingServiceImpl.INSTANCE.updateRatingByUser(user.getId(), articleId,
                                Rating.DISLIKED);
                        break;
                    }
                }
            } else {
                RatingServiceImpl.INSTANCE.addRatingByUser(user.getId(), articleId, Rating.DISLIKED);
            }
            commandResult = new CommandResult(UrlPath.VIEW_QUESTION_DO, CommandResult.TransitionType.REDIRECT);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to update or to add rating by user, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
