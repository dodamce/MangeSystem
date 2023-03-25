package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.User;
import sql.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/data")
public class user extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUser();
        resp.setContentType("application/json; charset=utf8");
        String Json = objectMapper.writeValueAsString(users);
        resp.getWriter().write(Json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String id = req.getParameter("id");
        userDAO.dropUser(Integer.parseInt(id));
        resp.sendRedirect("/MangeSystem/user.html");
    }
}
