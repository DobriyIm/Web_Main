package web.basics.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import web.basics.dao.UserDAO;
import web.basics.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class RegUserServlet extends HttpServlet {

    private final UserDAO userDAO;

    @Inject
    public RegUserServlet(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String regError = (String) session.getAttribute("regError");
        String regOk = (String) session.getAttribute("regOk");

        if(regError != null){
            req.setAttribute("regError", regError);
            session.removeAttribute("regError");
        }
        else if(regOk != null){
            req.setAttribute("regOk", regOk);
            session.removeAttribute("regOk");
        }

        req.setAttribute("pageBody", "reg.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = req.getParameter("userLogin");
        String userPassword = req.getParameter("userPassword");
        String confirmPass = req.getParameter("confirmPassword");
        String userName = req.getParameter("userName");

        HttpSession session = req.getSession();

        try{
            // region validation
            if(userLogin == null || userLogin.isEmpty()){
                throw new Exception("User Login could not be empty!");
            }
            if(!userDAO.isLoginFree(userLogin)){
                throw new Exception("This Login already in use!");
            }
            if(userPassword == null || userPassword.isEmpty() || userPassword.length() < 3){
                throw new Exception("User Password could not be empty!");
            }
            if(!confirmPass.equals(userPassword)){
                throw new Exception("Passwords do not match!");
            }
            if(userName == null || userName.isEmpty()){
                throw new Exception("User Name could not be empty!");
            }
            // endregion
            User user = new User();

            user.setLogin(userLogin);
            user.setPass(userPassword);
            user.setName(userName);

            if(userDAO.add(user) == null){
                throw new Exception("Server error: try later.");
            };

        }catch (Exception ex){
            session.setAttribute("regError", ex.getMessage());
            resp.sendRedirect(req.getRequestURI());
            return;
        }

        session.setAttribute("regOk", "Registration Successful!");

        resp.sendRedirect(req.getRequestURI());
    }
}
