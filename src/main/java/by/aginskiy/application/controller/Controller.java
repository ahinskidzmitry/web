package by.aginskiy.application.controller;

import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandManager;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.exception.CommandException;
import by.aginskiy.application.exception.ConnectionPoolException;
import by.aginskiy.application.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Main Controller class processes all requests from users.
 *
 * @author Dzmitry Ahinski
 */
@WebServlet(urlPatterns = { "*.do" }, name = "Controller")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Optional<Command> commandOptional = CommandManager.defineCommand(request);
        CommandResult commandResult = null;
        if(commandOptional.isPresent()) {
            Command command = commandOptional.get();
            try {
                commandResult = command.execute(request, response);
            } catch(CommandException exception) {
                logger.log(Level.ERROR, "Invalid command" + commandResult.getType());
                throw new ServletException(exception);
            }
        } else {
            logger.log(Level.ERROR, "Command was null. Reloading the current page.");
            commandResult = new CommandResult(JspPath.ERROR_404_PAGE, CommandResult.TransitionType.REDIRECT);
        }
        switch (commandResult.getType()) {
            case FORWARD: {
                request.getRequestDispatcher(commandResult.getPath()).forward(request, response);
                break;
            } case REDIRECT: {
                response.sendRedirect(request.getContextPath() + commandResult.getPath());
                break;
            } case REFRESH: {
                String pageToRefresh = request.getHeader(RequestParameter.HEADER_REFERER);
                response.sendRedirect(pageToRefresh);
                break;
            }
        }
    }

    public void destroy() {
        try {
            ConnectionPool.INSTANCE.destroyPool();
        } catch (ConnectionPoolException exception) {
            logger.log(Level.ERROR, "Connection pool has not been destroyed, ", exception);
        }
    }
}