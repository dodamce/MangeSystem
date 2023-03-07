package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
