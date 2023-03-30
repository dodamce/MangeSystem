package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.Money;
import sql.MoneyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/chart")
public class chart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型为 JSON
        response.setContentType("application/json");
        response.setContentType("application/json; charset=utf8");

        // 获取输出流
        PrintWriter out = response.getWriter();

        // 创建 Map 对象，保存图表数据
        Map<String, Object> data = new HashMap<>();
        MoneyDAO moneyDAO = new MoneyDAO();
        List<Money> lists = moneyDAO.selectAll();
        List<String> labels = new ArrayList<>();
        List<Integer> remain = new ArrayList<>();
        for (Money item : lists) {
            labels.add(item.getTime());
            remain.add(item.getRemain());
//            System.out.println(item.getRemain());
        }
        data.put("labels", labels);

        List<Map<String, Object>> datasets = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("label", "余额");
                    put("data", remain);
                    put("fill", false);
                    put("borderColor", "#007bff");
                    put("tension", 0.1);
                }}
        );

        data.put("datasets", datasets);

        // 将 Map 对象转换为 JSON 格式的字符串，并输出到客户端
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(data);
        out.println(jsonString);

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取数据
        resp.setContentType("application/json; charset=utf8");
        String date = req.getParameter("date");
        String in_num = req.getParameter("income");
        String out_num = req.getParameter("expense");
        String remain = req.getParameter("balance");

//        System.out.println(in_num);
        Money money = new Money();
        money.setTime(date);
        money.setIn_mun(Integer.parseInt(in_num));
        money.setOut_num(Integer.parseInt(out_num));
        money.setRemain(Integer.parseInt(remain));

        MoneyDAO moneyDAO = new MoneyDAO();
        moneyDAO.modify(money);
        resp.sendRedirect("/MangeSystem/money.html");
    }
}
