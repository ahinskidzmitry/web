package by.aginskiy.application.model.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorTest {

    @DataProvider
    public Object[][] names() {
        return new Object[][]{
                {"VALIDNAMEАпаv", true},
                {"validname", true},
                {"validNAME", true},
                {"validNAME522", true},
                {"92454341", true},
                {"TOOLARGENAMEINVALIDRDGDFgdffdsgdfsfdsgvdf", false},
                {"sma", false},
                {"sma<><><><><>", false},
                {null, false},
                {"", false},
        };
    }

    @DataProvider
    public Object[][] logins() {
        return new Object[][]{
                {"VALIDNAME", true},
                {"validname", true},
                {"validNAME", true},
                {"validNAME522", true},
                {"gfds92454341", true},
                {"TOOLARGENAMEINVALIDRDGDFgdffdsgdfsfdsgvdfergfresf", false},
                {"sm", false},
                {"sma<><><><><>", false},
                {null, false},
                {"", false},
        };
    }

    @DataProvider
    public Object[][] emails() {
        return new Object[][]{
                {"example@gmail.com", true},
                {"examp253@mail.ru", true},
                {"SOMEEMAIL123@yandex.by", true},
                {"invalid@.com", false},
                {"invalid@", false},
                {null, false},
                {"", false},
        };
    }

    @DataProvider
    public Object[][] passwords() {
        return new Object[][]{
                {"highPassword243", true},
                {"NotVeryHigh1", true},
                {"kuku", false},
                {"172432543543", false},
                {"D54W32RT43534R", false},
                {"dgdf3443fer", false},
                {null, false},
                {"", false},
        };
    }

    @Test(dataProvider = "names")
    public void testIsValidName(String name, boolean expected) {
        boolean actual = UserDataValidator.isValidName(name);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "logins")
    public void testIsValidLogin(String login, boolean expected) {
        boolean actual = UserDataValidator.isValidLogin(login);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "emails")
    public void testIsValidEmail(String email, boolean expected) {
        boolean actual = UserDataValidator.isValidEmail(email);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "passwords")
    public void testIsValidPassword(String password, boolean expected) {
        boolean actual = UserDataValidator.isValidPassword(password);
        assertEquals(actual, expected);
    }
}