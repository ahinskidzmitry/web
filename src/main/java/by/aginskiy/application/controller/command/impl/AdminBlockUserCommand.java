package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action command to block the user by the administrator.
 *
 * @author Dzmitry Ahinski
 */
public class AdminBlockUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(RequestParameter.USER_ID));
        CommandResult commandResult = null;
        try {
            UserServiceImpl.INSTANCE.blockUser(userId);
            commandResult = new CommandResult(UrlPath.ADMIN_TO_USER_PROFILE_DO, CommandResult.TransitionType.REDIRECT);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, "Error while trying to block user, ", exception);
            throw new CommandException(exception);
        }
        return commandResult;
    }
}
