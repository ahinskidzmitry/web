package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.UserServiceImpl;
import by.aginskiy.application.model.validator.UserDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to edit the user profile.
 *
 * @author Dzmitry Ahinski
 */
public class EditProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String name = (String) request.getAttribute(RequestParameter.NAME);
        String email = (String) request.getAttribute(RequestParameter.EMAIL);
        String login = (String) request.getAttribute(RequestParameter.LOGIN);
        boolean areValidFields = UserDataValidator.isValidName(name) && UserDataValidator.isValidEmail(email)
                && UserDataValidator.isValidLogin(login);
        CommandResult commandResult = null;
        try {
            if(areValidFields && !UserServiceImpl.INSTANCE.existsLoginIgnoreUser(user.getId(), login)) {
                user.setName(name);
                user.setEmail(email);
                user.setLogin(login);
                UserServiceImpl.INSTANCE.updateUser(user);
                session.setAttribute(SessionAttribute.USER, user);
                commandResult = new CommandResult(UrlPath.USER_ARTICLE_LIST_DO, CommandResult.TransitionType.REDIRECT);
            } else {
                request.setAttribute(RequestParameter.INVALID_DATA, true);
                commandResult = new CommandResult(JspPath.EDIT_PROFILE_PAGE, CommandResult.TransitionType.FORWARD);
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to update user, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
