package by.aginskiy.application.model.util;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Encrypts password
 *
 * @author Dzmitry Ahinski
 */
public class PasswordEncryptor {

    private static final Logger logger = LogManager.getLogger();

    public static String encrypt(String value) {
        String salt = BCrypt.gensalt(10);
        String encryptedValue = BCrypt.hashpw(value, salt);
        logger.log(Level.DEBUG, "Password was encrypted");
        return encryptedValue;
    }

    public static boolean check(String value, String encryptedValue) {
        boolean isEquals = BCrypt.checkpw(value, encryptedValue);
        return isEquals;
    }
}
