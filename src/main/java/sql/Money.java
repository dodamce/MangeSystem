package sql;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Money {
    private Timestamp time;
    private int in_mun;
    private int out_num;
    private int remain;

    public String getTime() {
        //时间戳到格式化时间的转化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);
    }

    public Timestamp getTimestamp() {
        return time;
    }


    public void setTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义日期格式
        try {
            Date date = sdf.parse(time); // 将字符串转换为Date对象
            this.time = new Timestamp(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIn_mun() {
        return in_mun;
    }

    public void setIn_mun(int in_mun) {
        this.in_mun = in_mun;
    }

    public int getOut_num() {
        return out_num;
    }

    public void setOut_num(int out_num) {
        this.out_num = out_num;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }
}
