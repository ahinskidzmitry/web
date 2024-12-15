package by.aginskiy.application.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter sets "UTF-8" encoding
 *
 * @author Dzmitry Ahinski
 */
public class EncodingFilter implements Filter {

    private static final String ENCODING_PARAM = "encoding";
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(ENCODING_PARAM);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestEncoding = request.getCharacterEncoding();
        if(encoding != null && !encoding.equalsIgnoreCase(requestEncoding)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        encoding = null;
    }
}
