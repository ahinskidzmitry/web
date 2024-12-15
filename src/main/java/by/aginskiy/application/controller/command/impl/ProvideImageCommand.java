package by.aginskiy.application.controller.command.impl;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandResult;
import by.aginskiy.application.model.util.CustomFileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Action command to display the entity photo.
 *
 * @author Dzmitry Ahinski
 */
public class ProvideImageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter(RequestParameter.FILE_NAME);
        if (!fileName.isEmpty()) {
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                Optional<byte[]> file = CustomFileReader.readFile(fileName);
                outputStream.write(file.get());
            } catch (IOException exception) {
                logger.log(Level.ERROR, exception);
            }
        }
        return new CommandResult(CommandResult.TransitionType.REFRESH);
    }
}
