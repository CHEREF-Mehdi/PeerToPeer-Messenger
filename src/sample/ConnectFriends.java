package sample;

import java.util.ArrayList;

/**
 * Created by Bit on 18/04/2017.
 */
public class ConnectFriends {

    public static ArrayList<client> clients=new ArrayList<>();

    public static boolean find(String s){
        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getAdrIP().equals(s)) return true;
        }
        return false;
    }

    public static client get(String s){

        for(int i=0;i<clients.size();i++){

            if(clients.get(i).getAdrIP().equals(s)) return clients.get(i);
        }
        return null;
    }





}
