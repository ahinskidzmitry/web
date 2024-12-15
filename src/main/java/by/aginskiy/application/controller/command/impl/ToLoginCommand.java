package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.command.CommandResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action command to go to the login page.
 *
 * @author Dzmitry Ahinski
 */
public class ToLoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        CommandResult commandResult = new CommandResult(JspPath.LOGIN_PAGE, CommandResult.TransitionType.REDIRECT);
        logger.log(Level.INFO, "Go to login page");
        return commandResult;
    }
}
