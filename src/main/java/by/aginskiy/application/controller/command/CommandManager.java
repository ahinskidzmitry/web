package by.aginskiy.application.controller.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

/**
 * Class used to operate with commands
 *
 * @author Dzmitry Ahinski
 */
public class CommandManager {

    private static final Logger logger = LogManager.getLogger();
    private static final String DO_SUBSTRING = ".do";
    private static final String SLASH_SYMBOL = "/";

    /**
     * Defines an Command from the request.
     *
     * @param request HttpServletRequest object, which may contain the Command.
     * @return empty Optional object if command is empty or not present in the request,
     * otherwise - Optional object of Command object.
     */
    public static Optional<Command> defineCommand(HttpServletRequest request) {
        Optional<Command> currentCommand;
        String url = request.getRequestURI();
        String commandName = parseCommandName(url);
        if(commandName != null && !commandName.isBlank()) {
            try {
                CommandType commandType = CommandType.valueOf(commandName.toUpperCase(Locale.ROOT));
                Command command = commandType.getCommand();
                currentCommand = Optional.of(command);
            } catch (IllegalArgumentException exception) {
                currentCommand = Optional.empty();
            }
        } else {
            currentCommand = Optional.empty();
        }
        logger.log(Level.DEBUG, commandName + " was defined");
        return currentCommand;
    }

    /**
     * Defines command type from request
     *
     * @param request HttpServletRequest object
     * @return Optional object of CommandType if command string is valid, otherwise empty Optional object.
     */
    public static Optional<CommandType> defineCommandType(HttpServletRequest request) {
        Optional<CommandType> commandTypeOptional = Optional.empty();
        String url = request.getRequestURI();
        String stringCommand = parseCommandName(url);
        if (stringCommand != null) {
            CommandType command = CommandType.valueOf(stringCommand.toUpperCase(Locale.ROOT));
            commandTypeOptional = Optional.of(command);
        }
        return commandTypeOptional;
    }

    /**
     * Parse url and define command name between last "/" add ".do"
     *
     * @param url request url
     * @return String as command name
     */
    public static String parseCommandName(String url) {
        String commandName;
        int doPosition = url.indexOf(DO_SUBSTRING);
        if (doPosition == -1) {
            return null;
        }
        int lastSlashPosition = url.lastIndexOf(SLASH_SYMBOL);
        commandName = url.substring(lastSlashPosition + 1, doPosition);
        return commandName;
    }
}
