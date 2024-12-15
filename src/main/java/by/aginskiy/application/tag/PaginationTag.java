package by.aginskiy.application.tag;

import by.aginskiy.application.controller.RequestParameter;
import by.aginskiy.application.controller.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

/**
 * Tag creates pagination.
 *
 * @author Dzmitry Ahinski
 */
public class PaginationTag extends TagSupport {

    public static final int PAGE_ENTITY_NUMBER = 10;
    public static final int PAGINATION_FIRST_PAGE = 1;
    private static final String THREE_POINTS_DISABLED_BUTTON = "...";
    private static final String FIRST_PAGE_BUTTON_VALUE = "1";
    private static final int MAX_BUTTON_NUMBER_BEFORE_CHANGE = 10;
    private String command;
    private String hidden;

    /**
     * Sets the command to click on the pagination element
     *
     * @param command current page command.
     *
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Sets current hidden parameter.
     *
     * @param hidden hidden parameter.
     *
     */
    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        int entityNumber = (int) request.getAttribute(RequestParameter.CURRENT_ENTITY_NUMBER);
        int pagesNumber = entityNumber / PAGE_ENTITY_NUMBER;
        if(entityNumber % 10 != 0) {
            pagesNumber++;
        }
        int currentPage = (int) session.getAttribute(SessionAttribute.PAGINATION_CURRENT_PAGE);
        createPagination(pageContext, currentPage, pagesNumber, command, hidden);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public static void createPagination(PageContext pageContext, int currentPage, int pagesNumber, String command,
                                        String hidden) {
        JspWriter out = pageContext.getOut();
        try {
            out.write("<div class=\"d-flex justify-content-center mb-3\">");
            out.write("<form method=\"post\" action=\"" + command.toLowerCase(Locale.ROOT) + ".do\">");
            if(hidden != null) {
                out.write("<input type=\"hidden\" name=\"criteria\" value=\"" + hidden + "\">");
            }
            if(pagesNumber > 1) {
                if(pagesNumber < MAX_BUTTON_NUMBER_BEFORE_CHANGE) {
                    for(int i = 1; i <= pagesNumber; i++) {
                        if(i == currentPage) {
                            createDisabledButton(out, Integer.toString(i));
                        } else {
                            createActiveButton(out, Integer.toString(i));
                        }
                    }
                } else {
                    if(currentPage < 6) {
                        for(int i = 1; i < 8; i++) {
                            if(i == currentPage) {
                                createDisabledButton(out, Integer.toString(i));
                            } else {
                                createActiveButton(out, Integer.toString(i));
                            }
                        }
                        createDisabledButton(out, THREE_POINTS_DISABLED_BUTTON);
                        createActiveButton(out, Integer.toString(pagesNumber));
                    } else if(currentPage > pagesNumber - 6) {
                        createActiveButton(out, FIRST_PAGE_BUTTON_VALUE);
                        createDisabledButton(out, THREE_POINTS_DISABLED_BUTTON);
                        for(int i = pagesNumber - 8; i <= pagesNumber; i++) {
                            if(i == currentPage) {
                                createDisabledButton(out, Integer.toString(i));
                            } else {
                                createActiveButton(out, Integer.toString(i));
                            }
                        }
                    } else {
                        createActiveButton(out, FIRST_PAGE_BUTTON_VALUE);
                        createDisabledButton(out, THREE_POINTS_DISABLED_BUTTON);
                        for(int i = currentPage - 3; i <= currentPage + 3; i++) {
                            if(i == currentPage) {
                                createDisabledButton(out, Integer.toString(i));
                            } else {
                                createActiveButton(out, Integer.toString(i));
                            }
                        }
                        createDisabledButton(out, THREE_POINTS_DISABLED_BUTTON);
                        createActiveButton(out, Integer.toString(pagesNumber));
                    }
                }
                out.write("</form>");
                out.write("</div>");
            }
        } catch (IOException | JspException exception) {
            exception.printStackTrace();
        }
    }

    private static void createDisabledButton(JspWriter out, String pageNumber) throws JspException {
        try {
            out.write("<button class=\"pagination-disabled\" disabled style=\"background-color: inherit; color: #DE5E60;\" disabled>" + pageNumber + "</button>");
        } catch (IOException exception) {
            throw new JspException(exception);
        }
    }

    private static void createActiveButton(JspWriter out, String pageNumber) throws JspException {
        try {
            out.write("<button type=\"submit\" class=\"pagination-link\" style=\"background-color: inherit; color: inherit;\" name=\"requested_page\" value=\"" + pageNumber + "\">");
            out.write(pageNumber + "</button>");
        } catch (IOException exception) {
            throw new JspException(exception);
        }
    }
}
