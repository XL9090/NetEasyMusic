package ltd.tongluren.Core;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 工具类，放一些静态数据和公共的方法
 */
public class Utils {
    /*下载好的文件存放的位置*/
    public static final String PATH="E:/MyMusic";
    /*通过该url进行爬取*/
    public static final String BASE_URL="https://music.163.com/discover/playlist";
    /*音频列表页面*/
    public static final String LIST_URL="https://music.163.com/playlist?id=";
    /*文件下载路劲，修改后面对应的id值来获取音频文件*/
    public static final String DOWNLOAD_URL="http://music.163.com/song/media/outer/url?id=";


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
