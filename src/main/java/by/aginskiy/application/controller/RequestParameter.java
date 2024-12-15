package by.aginskiy.application.controller;

/**
 * Represents constant names of request parameters and attributes.
 *
 * @author Dzmitry Ahinski
 */
public class RequestParameter {

    /**
     * Represents the language to change.
     */
    public static final String LANGUAGE = "language";

    /**
     * Represents the user name.
     */
    public static final String NAME = "name";

    /**
     * Represents the user's email address.
     */
    public static final String EMAIL = "email";

    /**
     * Represents the user's login.
     */
    public static final String LOGIN = "login";

    /**
     * Represents the user's password.
     */
    public static final String PASSWORD = "password";

    /**
     * Represents the confirmed user's password.
     */
    public static final String CONFIRMED_PASSWORD = "confirmed_password";

    /**
     * Represents the new user's password to change.
     */
    public static final String NEW_PASSWORD = "new_password";

    /**
     * Represents the confirmed new user's password to change.
     */
    public static final String CONFIRMED_NEW_PASSWORD = "confirmed_new_password";

    /**
     * Represents the user's current command.
     */
    public static final String COMMAND = "command";

    /**
     * Represents the invalid data entered by the user.
     */
    public static final String INVALID_DATA = "invalid_data";

    /**
     * Represents the blocked user status.
     */
    public static final String USER_BLOCKED = "user_blocked";

    /**
     * Represents the article's id.
     */
    public static final String ARTICLE_ID = "article_id";

    /**
     * Represents the article's topic.
     */
    public static final String ARTICLE_TOPIC = "topic";

    /**
     * Represents the article's text.
     */
    public static final String ARTICLE_TEXT = "text";

    /**
     * Represents the number of requested page.
     */
    public static final String REQUESTED_PAGE = "requested_page";

    /**
     * Represents the current entity list (Articles, Users, Comments).
     */
    public static final String CURRENT_ENTITY_LIST = "entity_list";

    /**
     * Represents the current entity number (Articles, Users, Comments).
     */
    public static final String CURRENT_ENTITY_NUMBER = "entity_number";

    /**
     * Represents the current header referer.
     */
    public static final String HEADER_REFERER = "REFERER";

    /**
     * Represents the file name to upload.
     */
    public static final String FILE_NAME = "file_name";

    /**
     * Represents the comment text.
     */
    public static final String COMMENT_TEXT = "comment_text";

    /**
     * Represents the comment id.
     */
    public static final String COMMENT_ID = "comment_id";

    /**
     * Represents the criteria to search.
     */
    public static final String CRITERIA = "criteria";

    /**
     * Represents the user's id.
     */
    public static final String USER_ID = "user_id";

    private RequestParameter() {
    }

}
