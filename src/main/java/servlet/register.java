package servlet;

import sql.User;
import sql.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        UserDAO userDAO = new UserDAO();
        userDAO.insert(user);
        resp.sendRedirect("/MangeSystem/log.html");
    }
}
