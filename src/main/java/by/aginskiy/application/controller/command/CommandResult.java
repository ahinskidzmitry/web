package by.aginskiy.application.controller.command;

/**
 * Contains 3 types of command result, used by Controller
 *
 * @author Dzmitry Ahinski
 */
public class CommandResult {

    private String path;
    private TransitionType type;

    public enum TransitionType {
        REDIRECT,
        FORWARD,
        REFRESH;
    }

    public CommandResult(TransitionType type) {
        this.type = type;
    }

    public CommandResult(String path, TransitionType type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public TransitionType getType() {
        return type;
    }
}
