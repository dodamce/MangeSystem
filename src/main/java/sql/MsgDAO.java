package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MsgDAO {
    public void insert(Mesage mesage) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "insert into mange.msg values(?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, mesage.getTime());
            statement.setString(2, mesage.getSender());
            statement.setString(3, mesage.getMessage());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }

    public List<Mesage> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result;
        List<Mesage> lists = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.msg";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                Mesage mesage = new Mesage();
                mesage.setTime(result.getTimestamp("time"));
                mesage.setSender(result.getString("sender"));
                mesage.setMessage(result.getString("message"));
                lists.add(mesage);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lists;
    }
}
