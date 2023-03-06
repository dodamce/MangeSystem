package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.Paper;
import sql.PaperDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
