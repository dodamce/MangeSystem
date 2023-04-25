# 中北大学信息对抗软件工程学期任务,企业公文管理系统+网络安全大作业

使用技术:

1. JavaEE Servlet
2. JDBC MySQL Jackson
3. HTML,CSS,JavaScript(jquery)
4. WebSocket
5. Junit 白盒测试

上线环境:Linux CentOS7

授课老师 - 杨光

现存账号:密码见数据库

# 1.0 开发完毕(2023.3.30 23:36:50) 现存功能

1. 用户管理(注册、登录、root 用户删除修改用户)
2. 资金管理(入账、出账 以图标的形式显示资金流动)
3. 聊天室(群聊功能)
4. 货存管理(进货出货影响资图表变变化)
5. 公文发布与审核

为了防止聊天信息数据库表项太多，这里在 Linux 下使用 crontab 命令设置定时任务，每天 0 点执行，清除数据库

# 2.0 黑盒测试在 2023.4.完成

主要测试内容：

1. 基本数据库 API 是否出错
2. 网页基础功能测试
3. 网站安全方面漏洞
4. 编写测试报告

# 3.0 目前正在网站相关安全漏洞进行修正(网络安全课程作业)
