package servlets;

import models.Property;
import store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeletePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        DbStore.instOf().deletePost(DbStore.instOf().findPostById(Integer.valueOf(id)));
        for (File file : new File(Property.returnValue("postsPhoto")).listFiles()) {
            String fileName = file.getName();
            if (id.equals(fileName.substring(0, fileName.indexOf('.')))) {
                file.delete();
                break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/usersPosts.do");
    }
}
