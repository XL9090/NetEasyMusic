package ltd.tongluren.dao;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection  getNewConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
           return DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_music","root","123456");
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
