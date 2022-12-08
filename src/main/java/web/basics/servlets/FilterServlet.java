package web.basics.servlets;

import com.google.inject.Singleton;
import web.basics.services.DataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FilterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataService dataService = (DataService) req.getAttribute("DataService");
        List<String> randData = new ArrayList<String>();
        try{
            Statement statement = dataService.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM randoms");

            while(res.next()){
                randData.add(res.getLong(1) + " | " + res.getLong(2) + " | " + res.getString(3));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        req.setAttribute("randData", randData.toArray(new String[0]));

        req.setAttribute("pageBody", "filter.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
