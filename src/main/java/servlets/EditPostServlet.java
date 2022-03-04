package servlets;

import models.Post;
import models.User;
import repositories.CarBodyRepository;
import repositories.CarMarkRepository;
import repositories.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class EditPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("posts", new ArrayList<>(PostRepository.instOf().findAllUsersPosts(user.getId())));
        req.getRequestDispatcher("usersPosts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Post post = Post.of(
                req.getParameter("name"),
                req.getParameter("description"),
                false,
                CarMarkRepository.instOf().findCarMarkById(Integer.valueOf(req.getParameter("mark"))),
                CarBodyRepository.instOf().findCarBodyById(Integer.valueOf(req.getParameter("body"))),
                (User) req.getSession().getAttribute("user"));
        post.setId(Integer.valueOf(req.getParameter("id")));
        PostRepository.instOf().addPost(post);
        resp.sendRedirect(req.getContextPath() + "/usersPosts.do");
    }
}
