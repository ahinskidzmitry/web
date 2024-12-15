package by.aginskiy.application.model.validator;

/**
 * Validate question.
 *
 * @author Dzmitry Ahinski
 */
public class QuestionDataValidator {

    public static boolean isValidTopic(String topic) {
        boolean result = true;
        if(topic == null) {
            result = false;
        } else if (topic.length() < 10 || topic.length() > 100) {
            result = false;
        }
        return result;
    }

    public static boolean isValidText(String text) {
        boolean result = true;
        if(text == null) {
            result = false;
        } else if (text.length() < 10 || text.length() > 1000) {
            result = false;
        }
        return result;
    }
}
