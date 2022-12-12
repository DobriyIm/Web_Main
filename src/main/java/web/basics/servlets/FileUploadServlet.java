package web.basics.servlets;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@MultipartConfig
@WebServlet("/f-upload")
@Singleton
public class FileUploadServlet extends HttpServlet {
    HttpSession session;
    List<String> extensions = Arrays.asList(new String[]{".jpg",".png",".bmp"});

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        session = req.getSession();

        if(session.getAttribute("fileError") != null){
            req.setAttribute("fileError", session.getAttribute("fileError"));
            session.removeAttribute("fileError");
        }
        if(session.getAttribute("imgMess") != null){
            req.setAttribute("imgMess", session.getAttribute("imgMess"));
            session.removeAttribute("image");
        }



        req.setAttribute("pageBody", "file.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Part img = req.getPart("file");

        try {

            if(img.getSize() < 0)
                throw new Exception("Unable to read file!");

            String imgFilename = img.getSubmittedFileName();
            int dotPosition = imgFilename.lastIndexOf('.');

            if (dotPosition == -1)
                throw new Exception("Files without extension not allowed!");

            String extension = imgFilename.substring(dotPosition);

            if(!extensions.contains(extension))
                throw new Exception("File type not supported!");

            String path = req.getServletContext().getRealPath("/");

            File uploaded = new File(path + "/../Uploads/" + imgFilename);

            Files.copy(img.getInputStream(), uploaded.toPath());

            session.setAttribute("imgMess", "File uploaded!");



        }catch (Exception ex){
            session.setAttribute("fileError", ex.getClass() + " : " +  ex.getMessage());
        }

        resp.sendRedirect(req.getRequestURI());
    }
}

