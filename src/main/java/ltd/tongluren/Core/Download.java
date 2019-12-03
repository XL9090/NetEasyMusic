package ltd.tongluren.Core;

import ltd.tongluren.model.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Download implements Runnable {
    /*文件保存路径*/
    private  String savePath="";
    /*Music队列 */
    private ConcurrentLinkedQueue<Music> songs=null;
    /*文件下载路径 +id*/
    private String downloadPath="";

    public Download(ConcurrentLinkedQueue<Music> links, String savePath,String downloadPath ){
        this.savePath=savePath;
        this.songs=links;
        this.downloadPath=downloadPath;
    }

    @Override
    public void run() {
        Music music=null;
        while (null!=(music=songs.poll())){
            long start=System.currentTimeMillis();
            download( this.savePath,downloadPath+music.getId(),music.getName());
            System.err.println("Download:"+(System.currentTimeMillis()-start)+"-----"+music.getName()+"------------"+Thread.currentThread().getId());
        }
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
            File musicFile=new File(savePath+"/"+name+".mp3");
            FileOutputStream writeFile=new FileOutputStream(musicFile);
            writeFile.write(read);
            urlConnection.disconnect();
        } catch (Exception e) {
            System.err.println("下载失败");
            e.printStackTrace();
        }
    }
}
