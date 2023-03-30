package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoneyDAO {
    public void modify(Money money) {
        //向数据库中添加资金链
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        if (money.getTime().equals("")) {
            return;
        }
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.money order by time desc limit 1;";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                int remain = Integer.parseInt(result.getString("remain"));
                System.out.println("money:" + money.getRemain());
                remain += money.getRemain();
                System.out.println("remain:" + remain);
                money.setRemain(remain);
                //重新插入到数据库中
                insert(money);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
    }


    public void insert(Money money) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Connect.getConnection();
            String sql = "insert into mange.money values(?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, money.getTimestamp());
            statement.setInt(2, money.getIn_mun());
            statement.setInt(3, money.getOut_num());
            statement.setInt(4, money.getRemain());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //获取数据库中的时间和余额
    public List<Money> selectAll() {
        //向数据库中添加资金链
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Money> lists = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "select * from mange.money";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                Money money = new Money();
                money.setRemain(Integer.parseInt(result.getString("remain")));
                money.setTime(result.getString("time"));
                lists.add(money);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.close(connection, statement, result);
        }
        return lists;
    }
}
