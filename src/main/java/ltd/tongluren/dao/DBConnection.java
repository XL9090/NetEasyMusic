package ltd.tongluren.dao;


import ltd.tongluren.Core.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection  getNewConnection(){
        try {
            Class.forName(Utils.getDriver());
           return DriverManager.getConnection(Utils.getUrl(),Utils.getUserName(),Utils.getPwd());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(AutoCloseable... closeable){
        for (AutoCloseable ac:closeable){
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
