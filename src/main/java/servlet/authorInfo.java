package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.Paper;
import sql.PaperDAO;
import sql.User;
import sql.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorInfo")
public class authorInfo extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取到指定公文ID->作者的用户id->的作者信息
        resp.setContentType("application/json; charset=utf8");
        String id = req.getParameter("paperId");
        if (id == null || "".equals(id)) {
            //参数缺少
            resp.getWriter().write("{\"ok:\"false,\"reason:\"\"参数缺失\"}");
        } else {
            //根据当前id查询数据库
            PaperDAO paperDAO = new PaperDAO();
            Paper paper = paperDAO.select(Integer.parseInt(id));
            if (paper.getUserId() == -1) {
                resp.getWriter().write("{\"ok:\"false,\"reason:\"\"查询博客不存在\"}");
                return;
            }
            //根据paper对象的用户ID找到作者信息
            UserDAO userDao = new UserDAO();
            User user = userDao.selectUserById(paper.getUserId());
            if ("".equals(user.getName())) {
                resp.getWriter().write("{\"ok:\"false,\"reason:\"\"查询用户不存在,非法操作\"}");
                return;
            }
            user.setPassword("");//密码不返回前端
            //将信息返回浏览器
            resp.getWriter().write(objectMapper.writeValueAsString(user));
        }
    }
}
