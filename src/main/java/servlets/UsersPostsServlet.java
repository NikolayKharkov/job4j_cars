package servlets;

import models.User;
import repositories.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class UsersPostsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("posts", new ArrayList<>(PostRepository.instOf().findAllUsersPosts(user.getId())));
        req.getRequestDispatcher("usersPosts.jsp").forward(req, resp);
    }
}