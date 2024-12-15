package by.aginskiy.application.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate user data.
 *
 * @author Dzmitry Ahinski
 */
public class UserDataValidator {

    private static final Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Zа-яА-Я0-9]{5,25}");
    private static final Pattern VALID_LOGIN_REGEX = Pattern.compile("^[a-zA-Z0-9]{5,30}");
    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}");
    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("((\\w)([-.](\\w))?)+@((\\w)([-.](\\w))?)+[a-zA-Z].{2,4}");

    public static boolean isValidLogin(String login) {
        if(login == null) {
            return false;
        }
        Matcher matcher = VALID_LOGIN_REGEX.matcher(login);
        boolean isValid = matcher.matches();
        return isValid;
    }

    public static boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        }
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        boolean isValid = matcher.matches();
        return isValid;
    }

    public static boolean isValidEmail(String email) {
        if(email == null) {
            return false;
        }
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        boolean isValid = matcher.matches();
        return isValid;
    }

    public static boolean isValidName(String name) {
        if(name == null) {
            return false;
        }
        Matcher matcher = VALID_NAME_REGEX.matcher(name);
        boolean isValid = matcher.matches();
        return isValid;
    }
}
