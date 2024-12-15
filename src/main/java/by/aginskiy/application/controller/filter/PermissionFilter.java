package by.aginskiy.application.controller.filter;

import by.aginskiy.application.controller.SessionAttribute;
import by.aginskiy.application.controller.command.Command;
import by.aginskiy.application.controller.command.CommandManager;
import by.aginskiy.application.controller.command.CommandType;
import by.aginskiy.application.controller.path.JspPath;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Filter checks user role and defines if user has such permissions.
 *
 * @author Dzmitry Ahinski
 */
public class PermissionFilter implements Filter {

    private static final Map<UserRole, EnumSet<CommandType>> permissionCommands = new HashMap<>();
    private static final int ERROR_403 = 403;

    @Override
    public void init(FilterConfig filterConfig) {

        EnumSet<CommandType> guestCommands = EnumSet.of(
                CommandType.TO_SIGN_UP,
                CommandType.TO_LOGIN,
                CommandType.REGISTER,
                CommandType.PROVIDE_IMAGE,
                CommandType.LOGIN,
                CommandType.LOCALE,
                CommandType.GLOBAL_ARTICLE_LIST
        );

        EnumSet<CommandType> clientCommands = EnumSet.of(
                CommandType.VIEW_COMMENT_ARTICLE,
                CommandType.VIEW_ARTICLE,
                CommandType.USER_COMMENT_LIST,
                CommandType.USER_ARTICLE_LIST,
                CommandType.TO_EDIT_PROFILE,
                CommandType.TO_EDIT_COMMENT,
                CommandType.TO_EDIT_ARTICLE,
                CommandType.TO_CHANGE_PASSWORD,
                CommandType.SIGN_OUT,
                CommandType.RECOVER_COMMENT,
                CommandType.RECOVER_ARTICLE,
                CommandType.PROVIDE_IMAGE,
                CommandType.LOCALE,
                CommandType.LIKE_COMMENT,
                CommandType.LIKE_ARTICLE,
                CommandType.HIDE_COMMENT,
                CommandType.HIDE_ARTICLE,
                CommandType.GLOBAL_ARTICLE_LIST,
                CommandType.EDIT_PROFILE,
                CommandType.EDIT_COMMENT,
                CommandType.EDIT_ARTICLE,
                CommandType.DISLIKE_ARTICLE,
                CommandType.CREATE_COMMENT,
                CommandType.CREATE_ARTICLE,
                CommandType.CHANGE_PASSWORD
        );

        EnumSet<CommandType> adminCommands = EnumSet.of(
                CommandType.SIGN_OUT,
                CommandType.PROVIDE_IMAGE,
                CommandType.LOCALE,
                CommandType.ADMIN_VIEW_ARTICLE,
                CommandType.ADMIN_USERS,
                CommandType.ADMIN_UNBLOCK_USER,
                CommandType.ADMIN_UNBLOCK_COMMENT,
                CommandType.ADMIN_TO_USER_PROFILE,
                CommandType.ADMIN_SEARCH_USERS,
                CommandType.ADMIN_SEARCH_ARTICLES,
                CommandType.ADMIN_BLOCK_USER,
                CommandType.ADMIN_BLOCK_COMMENT,
                CommandType.ADMIN_BLOCK_ARTICLE,
                CommandType.ADMIN_ARTICLES
        );

        permissionCommands.put(UserRole.GUEST, guestCommands);
        permissionCommands.put(UserRole.CLIENT, clientCommands);
        permissionCommands.put(UserRole.ADMIN, adminCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(SessionAttribute.USER);
        UserRole role;
        if(currentUser == null) {
            role = UserRole.GUEST;
        } else {
            role = currentUser.getRole();
        }
        Optional<Command> commandOptional = CommandManager.defineCommand(request);
        if(commandOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        EnumSet<CommandType> commands = permissionCommands.get(role);
        Optional<CommandType> commandTypeOptional = CommandManager.defineCommandType(request);
        if(commandTypeOptional.isPresent()) {
            CommandType commandType = commandTypeOptional.get();
            if(commands == null || !commands.contains(commandType)) {
                if(role.equals(UserRole.GUEST)) {
                    response.sendRedirect(request.getContextPath() + JspPath.LOGIN_PAGE);
                } else {
                    response.sendError(ERROR_403);
                }
                return;
            }
        } else {
            response.sendError(ERROR_403);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
