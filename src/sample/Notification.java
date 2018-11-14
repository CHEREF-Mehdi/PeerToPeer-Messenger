package sample;

/**
 * Created by Bit on 21/04/2017.
 */
public class Notification {
    private int type;
    private String FromIp;

    public Notification(int type, String fromIp) {
        this.type = type;
        FromIp = fromIp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFromIp() {
        return FromIp;
    }

    public void setFromIp(String fromIp) {
        FromIp = fromIp;
    }
}
