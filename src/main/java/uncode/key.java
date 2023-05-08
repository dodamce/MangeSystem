package uncode;

public class key {
    public String decode(String str) {
        //解密前端传入的加密数据
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            ret.append((char) (ch + 1));
        }
        return ret.toString();
    }
}
