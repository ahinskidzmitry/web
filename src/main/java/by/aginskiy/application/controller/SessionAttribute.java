package by.aginskiy.application.controller;

/**
 * Class represents constant names of session attributes.
 *
 * @author Dzmitry Ahinski
 */
public class SessionAttribute {

    /**
     * Represents the user's current language.
     */
    public static final String CURRENT_LANGUAGE = "current_language";

    /**
     * Represents the application's current language.
     */
    public static final String DEFAULT_LANGUAGE = "en_US";

    /**
     * Represents the current user object.
     */
    public static final String USER = "user";

    /**
     * Represents the current entity object (Article, User).
     */
    public static final String CURRENT_ENTITY = "current_entity";

    /**
     * Represents the pagination current page.
     */
    public static final String PAGINATION_CURRENT_PAGE = "current_page";

    private SessionAttribute() {
    }
}
