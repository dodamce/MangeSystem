package servlet;

import sql.KeyVailidDAO;
import sql.KeyValid;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/key")
public class key extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        KeyVailidDAO keyVailidDAO = new KeyVailidDAO();
        KeyValid keyValid = keyVailidDAO.selectByName(name);
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String str = df.format(date);//获取String类型的时间
        if (keyValid.isOverDute(str)) {
            // 返回重定型报文
            resp.sendRedirect("/MangeSystem/modefy.html");
        }

    }
}
