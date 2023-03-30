package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.Mesage;
import sql.MsgDAO;
import sql.Paper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/message")
public class message extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        List<Mesage> messages = new ArrayList<>();
        MsgDAO msgDAO = new MsgDAO();
        messages = msgDAO.selectAll();
        String Json = objectMapper.writeValueAsString(messages);
        resp.getWriter().write(Json);
    }
}
