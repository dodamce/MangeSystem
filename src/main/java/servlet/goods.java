package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.Goods;
import sql.GoodsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@WebServlet("/goods")
public class goods extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        GoodsDAO goodsDAO = new GoodsDAO();
        List<Goods> list = goodsDAO.selectAll();
        String Json = objectMapper.writeValueAsString(list);
        resp.getWriter().write(Json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        // 读取 HTTP POST 请求的 body 内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String payload = stringBuilder.toString();

        // 将 JSON 字符串转换成 Java 对象
        Goods goods = objectMapper.readValue(payload, Goods.class);
        GoodsDAO goodsDAO = new GoodsDAO();
        goodsDAO.insert(goods);
        List<Goods> list = goodsDAO.selectAll();
        String Json = objectMapper.writeValueAsString(list);
        resp.getWriter().write(Json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //删除货存
        String id = req.getParameter("id");
        resp.setContentType("application/json; charset=utf8");
        GoodsDAO goodsDAO = new GoodsDAO();
        goodsDAO.delete(Integer.parseInt(id));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //更新数据
        String msg = req.getParameter("id");
//        System.out.println(msg);
        resp.setContentType("application/json; charset=utf8");
        // 读取 HTTP POST 请求的 body 内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String payload = stringBuilder.toString();

        // 将 JSON 字符串转换成 Java 对象
        Goods goods = objectMapper.readValue(payload, Goods.class);
        goods.setId(Integer.parseInt(msg));

        if (goods.getName().equals("")) {
            return;
        }

        GoodsDAO goodsDAO = new GoodsDAO();
        goodsDAO.alter(goods);
    }
}
