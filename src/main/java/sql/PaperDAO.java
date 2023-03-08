package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//封装公文表的增删改查
public class PaperDAO {
    //添加公文
    public void insert(Paper paper) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            //构造sql语句
            String sql = "insert into mange.paper values(null,?,?,?,now(),?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, paper.getTitle());
            statement.setString(2, paper.getTitle());
            statement.setInt(3, paper.getUserId());
            statement.setInt(4, paper.getPass());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }

    //获取所有公文
    public List<Paper> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Paper> lists = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            //desc 按照降序排列
            String sql = "select * from mange.paper where pass=1 order by postTime desc ";
            statement = connection.prepareStatement(sql);

            result = statement.executeQuery();

            //变量结果集合
            while (result.next()) {
                Paper paper = new Paper();
                paper.setPaperId(result.getInt("paperId"));
                paper.setTitle(result.getString("title"));
                //如果摘要太长了，进行截取
                String content = result.getString("content");
                if (content.length() > 50) {
                    content = content.substring(0, 50) + "...";
                }
                paper.setContent(content);
                paper.setUserId(result.getInt("userId"));
                paper.setPostTime(result.getTimestamp("postTime"));
                paper.setPass(result.getInt("pass"));
                lists.add(paper);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
        return lists;
    }

    //获取所有未审核的公文
    public List<Paper> selectAllNoPass() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Paper> lists = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            //desc 按照降序排列
            String sql = "select * from mange.paper where pass=0 or pass=2 order by postTime desc ";
            statement = connection.prepareStatement(sql);

            result = statement.executeQuery();

            //变量结果集合
            while (result.next()) {
                Paper paper = new Paper();
                paper.setPaperId(result.getInt("paperId"));
                paper.setTitle(result.getString("title"));
                //如果摘要太长了，进行截取
                String content = result.getString("content");
                if (content.length() > 50) {
                    content = content.substring(0, 50) + "...";
                }
                paper.setContent(content);
                paper.setUserId(result.getInt("userId"));
                paper.setPostTime(result.getTimestamp("postTime"));
                paper.setPass(result.getInt("pass"));
                lists.add(paper);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
        return lists;
    }

    //根据公文id获取公文
    public Paper select(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Paper paper = new Paper();
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.paper where paperId=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, blogId);

            result = statement.executeQuery();//主键一定是唯一的
//            System.out.println(result);
            //变量结果集合
            if (result.next()) {
                paper.setPaperId(result.getInt("paperId"));
                paper.setTitle(result.getString("title"));
                paper.setContent(result.getString("content"));
                paper.setUserId(result.getInt("userId"));
//                System.out.println("DEBUG:" + result.getTimestamp("postTime"));
                paper.setPostTime(result.getTimestamp("postTime"));
                paper.setPass(result.getInt("pass"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
        return paper;
    }

    public void drop(int paperId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "delete from mange.paper where paperId=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, paperId);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }

    //修改公文审核权限
    public void changePermission(int paperId, int key) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "update mange.paper set pass=? where paperId=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, key);
            statement.setInt(2, paperId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }
}
