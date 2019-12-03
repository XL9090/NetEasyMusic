package ltd.tongluren.Core;

import ltd.tongluren.model.Music;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 调度器
 */
public class Handle {
    public static final ConcurrentLinkedQueue<String> Links=new ConcurrentLinkedQueue();
    public static final ConcurrentLinkedQueue<Music> Songs=new ConcurrentLinkedQueue();
    public static final ExecutorService executes = Executors.newFixedThreadPool(8);

    public void run(){
        String rootStr=Utils.get(Utils.BASE_URL);
        parseLinks( rootStr);
        executes.execute(new ParseMusic(Links,Utils.LIST_URL,Songs));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executes.execute(new Download(Songs,Utils.PATH,Utils.DOWNLOAD_URL));
        executes.execute(new Download(Songs,Utils.PATH,Utils.DOWNLOAD_URL));
        executes.execute(new Download(Songs,Utils.PATH,Utils.DOWNLOAD_URL));
        executes.execute(new Download(Songs,Utils.PATH,Utils.DOWNLOAD_URL));
    }

    private void parseLinks(String rootStr){
        Pattern pattern=Pattern.compile("/playlist\\?id=[\\d]+");
        Matcher matcher = pattern.matcher(rootStr);
        while (matcher.find()){
            String group = matcher.group();
            Links.offer(group);
        }
    }

}
