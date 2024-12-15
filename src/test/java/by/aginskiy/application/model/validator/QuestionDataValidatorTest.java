package by.aginskiy.application.model.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class QuestionDataValidatorTest {

    @DataProvider
    public Object[][] articleTopics() {
        return new Object[][]{
                {"SOME VALID TOPIC", true},
                {"some valid topic", true},
                {"some VALID TOPiC", true},
                {"SOME INVALID TOPIC because the text is too large,"
                        + "SOME INVALID TOPIC because the text is too large,"
                        + "SOME INVALID TOPIC because the text is too large,"
                        + "SOME INVALID TOPIC because the text is too large,"
                        + "SOME INVALID TOPIC because the text is too large"
                        + "SOME INVALID TOPIC because the text is too large"
                        + "SOME INVALID TOPIC because the text is too large", false},
                {"small", false},
                {null, false},
                {"", false},
        };
    }

    @DataProvider
    public Object[][] articleTexts() {
        return new Object[][]{
                {"SOME VALID TEXT", true},
                {"some valid text", true},
                {"some VALID tEXT", true},
                {"small", false},
                {"SMALL", false},
                {null, false},
                {"", false},
        };
    }

    @Test(dataProvider = "articleTopics")
    public void testIsValidTopic(String topic, boolean expected) {
        boolean actual = QuestionDataValidator.isValidTopic(topic);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "articleTexts")
    public void testIsValidText(String text, boolean expected) {
        boolean actual = QuestionDataValidator.isValidText(text);
        assertEquals(actual, expected);
    }
}