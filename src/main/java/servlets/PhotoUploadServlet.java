package servlets;

import models.Property;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import repositories.PostRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PhotoUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", req.getParameter("id"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/photoUpload.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File(Property.returnValue("postsPhoto"));
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (File file : new File(Property.returnValue("postsPhoto")).listFiles()) {
                String fileName = file.getName();
                if (id.equals(fileName.substring(0, fileName.indexOf('.')))) {
                    file.delete();
                    break;
                }
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder
                            + File.separator
                            + id
                            + item.getName().substring(item.getName().indexOf('.')));
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        PostRepository.instOf().updatePostPhotoStatus(Integer.valueOf(id));
        resp.sendRedirect(req.getContextPath() + "/usersPosts.do");
    }
}
