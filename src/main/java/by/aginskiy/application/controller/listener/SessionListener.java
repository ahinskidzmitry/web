package by.aginskiy.application.controller.listener;

import by.aginskiy.application.controller.SessionAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Initialize session with starting attributes
 *
 * @author Dzmitry Ahinski
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(SessionAttribute.CURRENT_LANGUAGE, SessionAttribute.DEFAULT_LANGUAGE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().invalidate();
    }
}
