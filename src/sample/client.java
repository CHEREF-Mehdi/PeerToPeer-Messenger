package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bit on 18/04/2017.
 */
public class client {

    private String adrIP;
    private String hostName;
    private int    Port;
    private int    notification;
    private ArrayList<InstantMessage> Msg;

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        if(notification!=0)
        this.notification++;
        else this.notification=0;

    }

    public client(String ip, String nm, String port){
        adrIP=ip;
        hostName=nm;
        Port= Integer.parseInt(port);

       Msg=new ArrayList<>();
        notification=0;


    }

    public String getAdrIP() {
        return adrIP;
    }

    public void setAdrIP(String adrIP) {
        this.adrIP = adrIP;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

    public ArrayList<InstantMessage> getMsg() {
        return Msg;
    }

    public void setMsg(ArrayList<InstantMessage> msg) {
        Msg = msg;
    }
}
