package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    //通过id查找货物
    public Goods select(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Goods goods = new Goods();
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.goods where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            result = statement.executeQuery();//主键一定是唯一的
//            System.out.println(result);
            //变量结果集合
            if (result.next()) {
                goods.setId(result.getInt("id"));
                goods.setName(result.getString("name"));
                goods.setPrice(result.getString("price"));
                goods.setQuantity(result.getString("quantity"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
        return goods;
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

            //添加购物信息，资金减少
            MoneyDAO moneyDAO = new MoneyDAO();
            Money money = new Money();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);//获取当前时间
            money.setTime(formattedDateTime);
            money.setIn_mun(0);
            int cost = Integer.parseInt(goods.getPrice()) * Integer.parseInt(goods.getQuantity());
            System.out.println("插入货物" + cost);
            money.setOut_num(cost);
            money.setRemain(-cost);
            moneyDAO.modify(money);

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

            //删除前，先把钱归还回来
            Goods goods = select(id);
            MoneyDAO moneyDAO = new MoneyDAO();
            Money money = new Money();

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);//获取当前时间
            money.setTime(formattedDateTime);//获取当前时间

            int cost = Integer.parseInt(goods.getPrice()) * Integer.parseInt(goods.getQuantity());
            money.setIn_mun(cost);
            money.setOut_num(0);
            money.setRemain(cost);
            System.out.println(cost);
            moneyDAO.modify(money);

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
