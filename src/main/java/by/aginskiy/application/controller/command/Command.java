package by.aginskiy.application.controller.command;

import by.aginskiy.application.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface that represents "Command" pattern. Used by a controller.
 *
 * @author Dzmitry Ahinski
 */
public interface Command {
    /**
     * Processes a request from controller and returns the page to be redirected.
     *
     * @param request request object from page.
     * @return CommandResult
     * @throws CommandException if an exception has occurred while executing.
     */
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
