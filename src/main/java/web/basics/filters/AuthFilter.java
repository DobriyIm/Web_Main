package web.basics.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import web.basics.dao.UserDAO;
import web.basics.entities.User;
import web.basics.services.DataService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@Singleton
public class AuthFilter implements Filter {
    private FilterConfig filterConfig;

    private final UserDAO userDAO;

    @Inject
    public AuthFilter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = (HttpSession) request.getSession();

        if(request.getParameter("logout") != null){
            session.removeAttribute("authUserId");
            response.sendRedirect(request.getContextPath());
            return;
        }

        if (request.getMethod().equalsIgnoreCase("POST")) {

            if("nav-auth-form".equals(request.getParameter("form-id"))){
                String userLogin = request.getParameter("userLogin");
                String userPass = request.getParameter("userPass");
                User user = userDAO.getUserByCredentials(userLogin,userPass);

                if(user == null){
                    session.setAttribute("authError", "Credentials incorrect!");
                }else{
                    session.setAttribute("authUserId", user.getId());
                }

                response.sendRedirect(request.getRequestURI());
                return;
            }

        }

        String authError = (String) session.getAttribute("authError");
        if(authError != null){
            request.setAttribute("authError", authError);
            session.removeAttribute("authError");
        }

        String userId = (String) session.getAttribute("authUserId");
        if(userId != null){
            request.setAttribute("authUser", userDAO.getUserById(userId));
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {
        filterConfig = null;
    }
}
