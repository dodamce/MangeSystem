package sql;

public class KeyValid {
    String name;
    String oldkey;
    String newKey;
    String time;

    public Boolean isOverDute(String time) {
        return false;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldkey() {
        return oldkey;
    }

    public void setOldkey(String oldkey) {
        this.oldkey = oldkey;
    }

    public String getNewKey() {
        return newKey;
    }

    public void setNewKey(String newKey) {
        this.newKey = newKey;
    }
}
