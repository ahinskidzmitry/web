package by.aginskiy.application.model.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SearchValidatorTest {

    @DataProvider
    public Object[][] searchData() {
        return new Object[][]{
                {"SOME VALID SEARCH", true},
                {"some valid search", true},
                {"some VALID seaRch", true},
                {"SOME INVALID SEARCH because the text is too large,"
                        + "SOME INVALID SEARCH because the text is too large,"
                        + "SOME INVALID SEARCH because the text is too large,"
                        + "SOME INVALID SEARCH because the text is too large,"
                        + "SOME INVALID SEARCH because the text is too large"
                        + "SOME INVALID SEARCH because the text is too large"
                        + "SOME INVALID SEARCH because the text is too large", false},
                {"small", false},
                {null, false},
                {"", false},
        };
    }

    @Test(dataProvider = "searchData")
    public void testIsValidTopic(String text, boolean expected) {
        boolean actual = SearchValidator.isValidSearchText(text);
        assertEquals(actual, expected);
    }
}