package by.aginskiy.application.controller.command;

import by.aginskiy.application.controller.command.impl.*;

/**
 * Contains all action commands
 *
 * @author Dzmitry Ahinski
 */
public enum CommandType {

    LOGIN (new LoginCommand()),
    SIGN_OUT (new SignOutCommand()),
    REGISTER (new RegisterCommand()),
    TO_LOGIN (new ToLoginCommand()),
    TO_SIGN_UP (new ToSignUpCommand()),
    GLOBAL_ARTICLE_LIST (new GlobalArticleListCommand()),
    LOCALE (new LocaleCommand()),
    CREATE_ARTICLE (new CreateArticleCommand()),
    USER_ARTICLE_LIST (new UserArticleListCommand()),
    LIKE_ARTICLE (new LikeArticleCommand()),
    DISLIKE_ARTICLE (new DislikeArticleCommand()),
    PROVIDE_IMAGE (new ProvideImageCommand()),
    VIEW_ARTICLE (new ViewArticleCommand()),
    CREATE_COMMENT (new CreateCommentCommand()),
    TO_EDIT_PROFILE (new ToEditProfileCommand()),
    EDIT_PROFILE (new EditProfileCommand()),
    TO_CHANGE_PASSWORD (new ToChangePasswordCommand()),
    LIKE_COMMENT (new LikeCommentCommand()),
    CHANGE_PASSWORD (new ChangePasswordCommand()),
    HIDE_ARTICLE (new HideArticleCommand()),
    RECOVER_ARTICLE (new RecoverArticleCommand()),
    TO_EDIT_ARTICLE (new ToEditArticleCommand()),
    EDIT_ARTICLE (new EditArticleCommand()),
    ADMIN_USERS (new AdminUsersCommand()),
    ADMIN_SEARCH_USERS (new AdminSearchUsersCommand()),
    ADMIN_ARTICLES (new AdminArticlesCommand()),
    ADMIN_SEARCH_ARTICLES (new AdminSearchArticlesCommand()),
    ADMIN_TO_USER_PROFILE (new AdminToUserProfileCommand()),
    ADMIN_VIEW_ARTICLE (new AdminViewArticleCommand()),
    ADMIN_BLOCK_ARTICLE (new AdminBlockArticleCommand()),
    USER_COMMENT_LIST (new UserCommentListCommand()),
    TO_EDIT_COMMENT (new ToEditCommentCommand()),
    EDIT_COMMENT (new EditCommentCommand()),
    HIDE_COMMENT (new HideCommentCommand()),
    RECOVER_COMMENT (new RecoverCommentCommand()),
    VIEW_COMMENT_ARTICLE (new ViewCommentArticleCommand()),
    ADMIN_BLOCK_USER (new AdminBlockUserCommand()),
    ADMIN_UNBLOCK_USER (new AdminUnblockUserCommand()),
    ADMIN_BLOCK_COMMENT (new AdminBlockCommentCommand()),
    ADMIN_UNBLOCK_COMMENT (new AdminUnblockCommentCommand());


    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
