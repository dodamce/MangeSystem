package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/text; charset=utf8");
        // 先找到当前用户的会话,将这个会话信息删掉
        HttpSession session = req.getSession(false);

        if (session == null) {
            // 用户没有登录
            resp.getWriter().write("用户没有登录,无法注销登录");
        } else {
            session.removeAttribute("user");
            // 重定向
            resp.sendRedirect("/MangeSystem/log.html");
        }
    }
}
