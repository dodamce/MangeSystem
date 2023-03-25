package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    //查找用户信息，用于登录
    public User SelectByName(String name) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.user where name=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, resultSet);
        }
        return null;
    }

    public User selectUserById(int id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        User user = new User();
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.user where id=?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            //查询结果为一个或者没有结果
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, resultSet);
        }
        return user;
    }

    public List<User> getAllUser() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<User> users = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "select* from mange.user";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            //将结果整理为集合
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));

                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void dropUser(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "delete from mange.user where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        try {
            connection = Connect.getConnection();
            String sql = "insert into mange.user values(null,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }
}
