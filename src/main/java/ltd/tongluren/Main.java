package ltd.tongluren;


import ltd.tongluren.Core.Utils;
import ltd.tongluren.dao.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    /**
     * 连接数据库把数据存储起来，
     * 验证歌曲唯一，不重复
     * 多次爬取分类页面
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
       // new Handle().run();
        //String s = Utils.get("https://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=105");
        //System.out.println(s);
        Connection connection = DBConnection.getConnection();
        System.out.println(connection);
        DBConnection.backConnection(connection);

    }

}
