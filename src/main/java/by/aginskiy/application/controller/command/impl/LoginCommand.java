package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.service.impl.UserServiceImpl;
import by.aginskiy.application.model.validator.UserDataValidator;
import by.aginskiy.application.tag.PaginationTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Action command for user login.
 *
 * @author Dzmitry Ahinski
 */
public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String login = (String) request.getAttribute(RequestParameter.LOGIN);
        String password = (String) request.getAttribute(RequestParameter.PASSWORD);
        CommandResult commandResult = null;
        try {
            if(UserDataValidator.isValidLogin(login) && UserDataValidator.isValidPassword(password)) {
                Optional<User> userOptional = UserServiceImpl.INSTANCE.login(login, password);
                if(userOptional.isPresent()) {
                    User currentUser = userOptional.get();
                    if(currentUser.isBlocked()) {
                        request.setAttribute(RequestParameter.USER_BLOCKED, true);
                        commandResult = new CommandResult(JspPath.LOGIN_PAGE, CommandResult.TransitionType.FORWARD);
                        logger.log(Level.DEBUG, "The user is blocked: " + login);
                    } else {
                        session.setAttribute(SessionAttribute.USER, currentUser);
                        switch (currentUser.getRole()) {
                            case CLIENT: {
                                commandResult = new CommandResult(UrlPath.USER_ARTICLE_LIST_DO, CommandResult.TransitionType.REDIRECT);
                                break;
                            } case ADMIN: {
                                commandResult = new CommandResult(UrlPath.ADMIN_USERS_DO, CommandResult.TransitionType.REDIRECT);
                                break;
                            }
                        }
                        session.setAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE, PaginationTag.PAGINATION_FIRST_PAGE);
                    }
                } else {
                    request.setAttribute(RequestParameter.INVALID_DATA, true);
                    commandResult = new CommandResult(JspPath.LOGIN_PAGE, CommandResult.TransitionType.FORWARD);
                    logger.log(Level.DEBUG, "The user does not exist: " + login);
                }
            } else {
                request.setAttribute(RequestParameter.INVALID_DATA, true);
                commandResult = new CommandResult(JspPath.LOGIN_PAGE, CommandResult.TransitionType.FORWARD);
                logger.log(Level.DEBUG, "The user entered an invalid data!");
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "User entrance is not possible. Username: " + login, exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
