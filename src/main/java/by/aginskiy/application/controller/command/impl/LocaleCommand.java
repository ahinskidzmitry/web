package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to change the current locale.
 *
 * @author Dzmitry Ahinski
 */
public class LocaleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String currentLanguage = request.getParameter(RequestParameter.LANGUAGE);
        session.setAttribute(SessionAttribute.CURRENT_LANGUAGE, currentLanguage);
        CommandResult result = new CommandResult(CommandResult.TransitionType.REFRESH);
        logger.log(Level.DEBUG, "The interface language has been changed to " + currentLanguage);
        return result;
    }
}
