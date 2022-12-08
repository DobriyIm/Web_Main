package web.basics.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Singleton
public class DemoFilter implements Filter {

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setAttribute("from-filter", "Filter works!");
        filterChain.doFilter(servletRequest,servletResponse);

    }

    public void destroy() {
        filterConfig = null;
    }
}
