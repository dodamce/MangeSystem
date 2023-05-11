package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import uncode.key;


@WebServlet("/log")
public class log extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应解析编码
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        System.out.println(req.getRemoteAddr());
        // 获取请求参数，和数据库的内容进行比较，比较成功创建会话
        String name = req.getParameter("name");
        String password = req.getParameter("password");
//        System.out.println("DEBUG BEFORE:" + name + " " + password);
        key decode = new key();
        password = decode.decode(password);
//        System.out.println("DEBUG AFTER:" + name + " " + password);

        if (null == name || "".equals(name) || null == password || "".equals(password)) {
            // 数据不全
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("用户名或密码为空");
            return;
        }
        UserDAO user_dao = new UserDAO();
        User user = user_dao.SelectByName(name);
        LogTimeDAO logTimeDAO = new LogTimeDAO();
        FrozenDAO frozenDAO = new FrozenDAO();

        if (user == null && frozenDAO.find(name) != null) {
            // 没有这个人或密码不匹配
            LogTime tmp = logTimeDAO.selectByname(name);
            int curtime = tmp.getLogtimes();
            if (curtime == 0) {
                frozenDAO.insert(name);
                resp.setContentType("text/html; charset=utf8");
                resp.getWriter().write("用户名今日登录错误次数过多，已被冻结");
            }
            tmp.setLogtimes(curtime);
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("用户名不存在");
            return;
        }
        if (!password.equals(user.getPassword())) {
            // 没有这个人或密码不匹配
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("密码错误,您输入的密码为:" + password);
            return;
        }
        // 创建会话
        HttpSession httpSession = req.getSession(true);
        // 将用户信息存到会话中
        httpSession.setAttribute("user", user);

        // 返回重定型报文
        resp.sendRedirect("/MangeSystem/index.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            // 没有登录
            User user = new User();
            resp.getWriter().write(objectMapper.writeValueAsString(user));
            return;
        }
        User userReq = (User) httpSession.getAttribute("user");
        if (userReq == null) {
            // 没有登录
            User user = new User();
            resp.getWriter().write(objectMapper.writeValueAsString(user));
            return;
        }

        // 已经登录,不要把密码返回前端
        userReq.setPassword("");
        resp.getWriter().write(objectMapper.writeValueAsString(userReq));
    }
}
