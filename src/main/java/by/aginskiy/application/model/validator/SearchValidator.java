package by.aginskiy.application.model.validator;

/**
 * Validate search.
 *
 * @author Dzmitry Ahinski
 */
public class SearchValidator {

    public static boolean isValidSearchText(String text) {
        boolean result = true;
        if(text == null || text.isEmpty()) {
            result = false;
        } else if (text.length() < 10 || text.length() > 50) {
            result = false;
        }
        return result;
    }
}
