package ltd.tongluren.dao;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DBConnection {
    private  static final ConcurrentLinkedQueue<Connection> Links=new ConcurrentLinkedQueue();
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<3;i++){
            try {
                Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/easy_music","root","123456");
                Links.offer(conn);
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection(){
        System.out.println(Links);
        Connection conn=Links.poll();
        System.out.println(Links.size());
        return  conn;
    }
    public static void backConnection(Connection connection){
        Links.offer(connection);
        System.out.println(Links.size());
        System.out.println(Links);
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
