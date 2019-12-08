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
    public static final ConcurrentLinkedQueue<String> Links = new ConcurrentLinkedQueue();
    public static final ConcurrentLinkedQueue<Music> Songs = new ConcurrentLinkedQueue();
    public static final ExecutorService executes = Executors.newFixedThreadPool(4);

    public void run() {
        executes.execute(new ParseLink());
        executes.execute(new ParseMusic(Utils.LIST_URL));
        executes.execute(new Download(Utils.getPATH(),Utils.DOWNLOAD_URL));
        executes.execute(new Download(Utils.getPATH(),Utils.DOWNLOAD_URL));
    }
}
