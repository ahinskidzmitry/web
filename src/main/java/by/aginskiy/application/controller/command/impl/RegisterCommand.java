package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.UserRole;
import by.aginskiy.application.model.service.impl.UserServiceImpl;
import by.aginskiy.application.model.validator.UserDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action command to create a new user account.
 *
 * @author Dzmitry Ahinski
 */
public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final boolean DEFAULT_IS_BLOCKED = false;
    private static final UserRole DEFAULT_ROLE = UserRole.CLIENT;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String name = (String) request.getAttribute(RequestParameter.NAME);
        String email = (String) request.getAttribute(RequestParameter.EMAIL);
        String login = (String) request.getAttribute(RequestParameter.LOGIN);
        String password = (String) request.getAttribute(RequestParameter.PASSWORD);
        User user = new User(name, email, login, DEFAULT_IS_BLOCKED, DEFAULT_ROLE);
        boolean isValidData = UserDataValidator.isValidName(name) && UserDataValidator.isValidEmail(email)
                && UserDataValidator.isValidLogin(login) && UserDataValidator.isValidPassword(password);
        try {
            if(isValidData && UserServiceImpl.INSTANCE.register(user, password)) {
                UserServiceImpl.INSTANCE.sendMail(email);
                request.setAttribute(RequestParameter.INVALID_DATA, false);
                logger.log(Level.DEBUG, "Account registered successfully");
            } else {
                request.setAttribute(RequestParameter.INVALID_DATA, true);
                logger.log(Level.DEBUG, "The user entered an invalid data!");
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to register user, ", exception);
            throw new CommandException(exception);
        }
        return new CommandResult(JspPath.REGISTRATION_PAGE, CommandResult.TransitionType.FORWARD);
    }
}
