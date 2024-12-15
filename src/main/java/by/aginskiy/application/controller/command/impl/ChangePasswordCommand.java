package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;
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
 * Action command to change the password by the user.
 *
 * @author Dzmitry Ahinski
 */
public class ChangePasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String password = (String) request.getAttribute(RequestParameter.PASSWORD);
        String confirmedPassword = (String) request.getAttribute(RequestParameter.CONFIRMED_PASSWORD);
        String newPassword = (String) request.getAttribute(RequestParameter.NEW_PASSWORD);
        String confirmedNewPassword = (String) request.getAttribute(RequestParameter.CONFIRMED_NEW_PASSWORD);
        boolean areValidFields = UserDataValidator.isValidPassword(password)
                && UserDataValidator.isValidPassword(confirmedPassword)
                && UserDataValidator.isValidPassword(newPassword)
                && UserDataValidator.isValidPassword(confirmedNewPassword);
        try {
            if(areValidFields && UserServiceImpl.INSTANCE.isPasswordCanBeChanged(user.getId(), password,
                    confirmedPassword, newPassword, confirmedNewPassword)) {
                UserServiceImpl.INSTANCE.updatePassword(user.getId(), newPassword);
                request.setAttribute(RequestParameter.INVALID_DATA, false);
            } else {
                request.setAttribute(RequestParameter.INVALID_DATA, true);
                logger.info("The user entered an invalid data! ");
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to update user password, ", exception);
            throw new CommandException(exception);
        }
        return new CommandResult(JspPath.CHANGE_PASSWORD_PAGE, CommandResult.TransitionType.FORWARD);
    }
}
