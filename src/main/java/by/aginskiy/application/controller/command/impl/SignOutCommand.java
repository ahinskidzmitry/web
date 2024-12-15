package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action command to log out the user.
 *
 * @author Dzmitry Ahinski
 */
public class SignOutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String currentLanguage = (String) session.getAttribute(SessionAttribute.CURRENT_LANGUAGE);
        session.invalidate();
        session = request.getSession();
        session.setAttribute(SessionAttribute.CURRENT_LANGUAGE, currentLanguage);
        return new CommandResult(JspPath.LOGIN_PAGE, CommandResult.TransitionType.REDIRECT);
    }
}
