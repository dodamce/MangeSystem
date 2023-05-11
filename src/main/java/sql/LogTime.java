package sql;

public class LogTime {
    int id;
    String name;
    int logtimes;

    LogTime() {
        id = -1;
        name = "root";
        logtimes = 3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogtimes() {
        return logtimes;
    }

    public void setLogtimes(int logtimes) {
        this.logtimes = logtimes;
    }
}
