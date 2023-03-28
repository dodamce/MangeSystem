package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO {
    public List<Goods> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Goods> lists = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.goods";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                Goods goods = new Goods();
                goods.setId(result.getInt("id"));
                goods.setName(result.getString("name"));
                goods.setPrice(result.getString("price"));
                goods.setQuantity(result.getString("quantity"));
                lists.add(goods);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
        return lists;
    }

    public void insert(Goods goods) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "insert into mange.goods values(null,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getPrice());
            statement.setString(3, goods.getQuantity());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }

    public void alter(Goods goods) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "update mange.goods set name=?,price=?,quantity=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getPrice());
            statement.setString(3, goods.getQuantity());
            statement.setInt(4, goods.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connect.getConnection();
            String sql = "delete from mange.goods where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, null);
        }
    }
}
