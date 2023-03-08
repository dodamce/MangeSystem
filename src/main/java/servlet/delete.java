package servlet;

import sql.Paper;
import sql.PaperDAO;
import sql.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete")
public class delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //检查用户登录情况
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("用户未登录，无法删除公文");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("用户未登录，无法删除公文");
            return;
        }

        //获取要删除的公文id，并检查博客是否存在
        String paperId = req.getParameter("paperId");
        if (paperId == null || "".equals(paperId)) {

            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("提交参数不正确");
            return;
        }

        PaperDAO paperDAO = new PaperDAO();
        Paper paper = paperDAO.select(Integer.parseInt(paperId));

        if (paper.getPaperId() == -1) {

            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("要删除的公文不存在");
            return;
        }

        paperDAO.drop(Integer.parseInt(paperId));

        //重定向到博客列表页
        resp.sendRedirect("/MangeSystem/passList.html");
    }
}
