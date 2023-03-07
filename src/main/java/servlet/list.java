package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@WebServlet("/list")
public class list extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求公文列表请求
        //尝试获取请求中的blogId,如果这个参数存在，说明是要请求公文id
        String msg = req.getParameter("paperId");
        PaperDAO paperDAO = new PaperDAO();
        resp.setContentType("application/json; charset=utf8");
        if (msg == null) {
            //没有具体的公文ID，直接获取所有的公文列表
            List<Paper> paperList = paperDAO.selectAll();
            //将blogList的数据转化为Json格式
            String Json = objectMapper.writeValueAsString(paperList);
//        System.out.println(Json);
            resp.getWriter().write(Json);
        } else {
            //获取具体的公文详情
            int paperId = Integer.parseInt(msg);
//            System.out.println(blogId);
            Paper paper = paperDAO.select(paperId);
//            System.out.println(blog.getPostTime());
            String Json = objectMapper.writeValueAsString(paper);
            resp.getWriter().write(Json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");

        HttpSession session = req.getSession(false);
        if (session == null) {

            //用户未登录
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("用户未登录");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            //用户未登录
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("用户未登录");
            return;
        }

        //从请求中提取标题和正文
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || "".equals(title)) {
            //请求参数失败
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("提交博客失败，缺少参数");
            return;
        }

        //插入数据库中
        //公示标题，作者信息需要手动设置，上传时间和公文id在插入数据库中自动生成
        Paper paper = new Paper();
        paper.setTitle(title);
        paper.setContent(content);

        //作者信息在登录的session会话上
        paper.setUserId(user.getId());

        paper.setPass(2);

        PaperDAO paperDAO = new PaperDAO();
        paperDAO.insert(paper);

        //从定向公文列表页
        resp.sendRedirect("/MangeSystem/list.html");
    }
}
