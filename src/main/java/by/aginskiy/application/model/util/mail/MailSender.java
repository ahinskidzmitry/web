package by.aginskiy.application.model.util.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Class to send mail messages
 *
 * @author Dzmitry Ahinski
 */
public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTY_PATH = "/config/mail.properties";
    private static final String CONTENT_TYPE = "text/html";

    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    public MailSender(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }

    /**
     *
     * Sends mail.
     *
     */
    public void sendMessage() {
        properties = new Properties();
        try {
            initMessage();
            Transport.send(message);
        } catch (AddressException exception) {
            logger.error("Invalid address: " + sendToEmail + "  " + exception);
        } catch (MessagingException exception) {
            logger.error("Error sending message: " + exception);
        }
    }

    /**
     *
     * Initializes message.
     *
     * @throws MessagingException if an error occurs while processing.
     *
     */
    private void initMessage() throws MessagingException {
        try {
            properties.load(MailSender.class.getResourceAsStream(PROPERTY_PATH));
        } catch (IOException exception) {
            logger.error("Unable to read file by path " + PROPERTY_PATH + " " + exception);
        }
        Session mailSession = MailSessionFactory.createSession(properties);
        message = new MimeMessage(mailSession);
        message.setSubject(mailSubject);
        message.setContent(mailText, CONTENT_TYPE);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
    }
}