package ltd.tongluren.Core;

import ltd.tongluren.dao.DBConnection;
import ltd.tongluren.dao.MusicDao;
import ltd.tongluren.model.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Download implements Runnable {
    /*文件保存路径*/
    private  String savePath="";
    /*文件下载路径 +id*/
    private String downloadPath="";
    private static ConcurrentLinkedQueue<Map<String,Object>> musicQueue=null;

    static {
        new Thread(()->{
            Connection conn= DBConnection.getNewConnection();
            musicQueue=new ConcurrentLinkedQueue<>();
            MusicDao musicDao=new MusicDao();
            //3秒调用一次，每次
            while (true){
                if(musicQueue.size()>5){
                    continue;
                }
                List<Map<String, Object>> musics = musicDao.pop(conn, 10);
                if(musics==null){
                    return;
                }
                musicQueue.addAll(musics);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Download( String savePath,String downloadPath ){
        this.savePath=savePath;
        this.downloadPath=downloadPath;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Connection conn=DBConnection.getNewConnection();
        MusicDao musicDao=new MusicDao();
        while (null!=musicQueue.peek()){
            Map<String, Object> id = musicQueue.poll();
            download(this.savePath,this.downloadPath+id.get("id"),id.get("id").toString()+".mp3");
            musicDao.setDownload(conn, id.get("id").toString());
            System.err.println(id.get("id")+"下载成功");
        }
        System.out.println("-------------------"+musicQueue.size()+"----------------------");

    }


    private  void download(String savePath,String linkUrl,String name){
        try {
            URL url=new URL(linkUrl);
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            byte[] read = Utils.read(inputStream);
            inputStream.close();
            File file=new File(savePath);
            if(!file.exists()){
                file.mkdir();
            }
            File musicFile=new File(savePath+"/"+name);
            FileOutputStream writeFile=new FileOutputStream(musicFile);
            writeFile.write(read);
            urlConnection.disconnect();
        } catch (Exception e) {
            System.err.println("下载失败");
            e.printStackTrace();
        }
    }
}
