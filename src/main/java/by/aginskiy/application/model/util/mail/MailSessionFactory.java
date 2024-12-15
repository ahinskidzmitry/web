package by.aginskiy.application.model.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Class that creates Mail Session.
 *
 * @author Dzmitry Ahinski
 */
public class MailSessionFactory {

    private static final String MAIL_USERNAME = "mail.user.name";
    private static final String MAIL_PASSWORD = "mail.user.password";

    /**
     *
     * Creates Mail Session.
     *
     */
    public static Session createSession(Properties config) {
        String username = config.getProperty(MAIL_USERNAME);
        String password = config.getProperty(MAIL_PASSWORD);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        return Session.getDefaultInstance(config, authenticator);
    }
}
