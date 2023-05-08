package uncode;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class LoginListener implements ServletContextListener {
    private Map<String, Integer> loginAttempts = new HashMap<>();  // 记录用户登录失败次数
    private Map<String, Long> frozenUsers = new HashMap<>();  // 记录被冻结的用户账号和冻结时间

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("loginAttempts", loginAttempts);
        context.setAttribute("frozenUsers", frozenUsers);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // do nothing
    }
}
