package ltd.tongluren.Core;

import ltd.tongluren.model.Music;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析html 获取music对象
 */
public class ParseMusic implements Runnable {
    private ConcurrentLinkedQueue<String> links=null;
    public  ConcurrentLinkedQueue<Music> Songs=null;
    private String baseLink;

    public ParseMusic(ConcurrentLinkedQueue<String> links,String baseLink,ConcurrentLinkedQueue<Music> Songs){
        this.Songs=Songs;
        this.links=links;
        this.baseLink=baseLink;
    }
    @Override
    public void run() {
        String temp="";
        while (null!=(temp=links.poll())){
            temp=temp.split("=")[1];
            String resp=Utils.get(baseLink+temp);
            parseSong(resp);
        }
    }

    public  void parseSong(String respData){
        Pattern pattern=Pattern.compile("<a href=\"/song\\?id=[\\d]+\">[\\S]+</a>");
        Matcher matcher = pattern.matcher(respData);
        while (matcher.find()){
            String group = matcher.group();
            System.out.println(group+"------------"+Thread.currentThread().getId());
            String name=group.substring(group.indexOf(">")+1,group.lastIndexOf("<"));
            Pattern p=Pattern.compile("id=[\\d]+");
            Matcher m = p.matcher(group);
            m.find();
            Music music = new Music(name,m.group().split("=")[1]);
            System.out.println(music);
            Songs.offer(music);

        }
    }
}
