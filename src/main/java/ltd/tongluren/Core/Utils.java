package ltd.tongluren.Core;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 工具类，放一些静态数据和公共的方法
 */
public class Utils {
    /*下载好的文件存放的位置*/
    private  static  String PATH="";
    /*分类图片保存路径*/
    private  static  String IMG_PATH="";
    /*数据库连接驱动*/
    private static String Driver="";
    /*数据库连接地址*/
    private static String Url="";
    /*数据库连接用户名*/
    private static String UserName="";
    /*数据库连接密码*/
    private static String Pwd="";

    /*通过该url进行爬取*/
    public static final String BASE_URL="https://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=";
    /*音频列表页面*/
    public static final String LIST_URL="https://music.163.com/playlist?id=";
    /*文件下载路劲，修改后面对应的id值来获取音频文件*/
    public static final String DOWNLOAD_URL="http://music.163.com/song/media/outer/url?id=";

    /*初始化参数*/
    static {
        InputStream configfile = Utils.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties=new Properties();
        try {
            properties.load(configfile);
            Utils.PATH= (String) properties.get("path");
            Utils.IMG_PATH=(String) properties.get("img");
            Utils.Driver=(String) properties.get("jdbc.drive");
            Utils.Url=(String) properties.get("jdbc.url");
            Utils.UserName=(String) properties.get("jdbc.user");
            Utils.Pwd=(String) properties.get("jdbc.pwd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPATH() {
        return PATH;
    }

    public static String getImgPath() {
        return IMG_PATH;
    }

    public static String getDriver() {
        return Driver;
    }

    public static String getUrl() {
        return Url;
    }

    public static String getUserName() {
        return UserName;
    }

    public static String getPwd() {
        return Pwd;
    }

    /**
     *  通过url地址拿到html文本
     * @param link
     * @return
     */
    public static String get(String link){
        try {
            URL url=new URL(link);
            HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            byte[] totalData = read(in);
            String respData=new String(totalData);
            conn.disconnect();
            return respData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 冲流中读取数据
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[]  read(InputStream in) throws IOException {
        byte[] buff=new byte[1024*1024];
        int len=0;
        byte[] total=new  byte[0];
        while ((len=in.read(buff))!=-1){
            byte[] temp=new byte[total.length+len];
            System.arraycopy(total,0,temp,0,total.length);
            System.arraycopy(buff,0,temp,total.length,len);
            total=temp;
        }
        return total;
    }

}
