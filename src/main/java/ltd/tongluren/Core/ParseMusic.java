package ltd.tongluren.Core;

import ltd.tongluren.dao.CategoryDao;
import ltd.tongluren.dao.DBConnection;
import ltd.tongluren.dao.MusicDao;
import ltd.tongluren.dao.RelationshipDao;
import ltd.tongluren.model.Music;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析html 获取music对象
 */
public class ParseMusic implements Runnable {
    public static ConcurrentLinkedQueue<Map<String, Object>> musicQueue = null;
    private String baseLink;
    private MusicDao musicDao = null;
    private CategoryDao categoryDao = null;
    private RelationshipDao relationshipDao =null;
    private Connection conn = null;

    static {
        musicQueue = new ConcurrentLinkedQueue<>();
        new Thread(() -> {
            CategoryDao categoryDao = new CategoryDao();
            Connection conn= DBConnection.getNewConnection();
            while (true) {
                if (musicQueue.size() < 5) {
                    List<Map<String, Object>> musicCategorys = categoryDao.popNotDownload(conn, 10);
                    if (null == musicCategorys) {
                        return;
                    }
                    musicQueue.addAll(musicCategorys);
                    System.out.println(musicQueue.size());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public ParseMusic(String baseLink) {
        conn = DBConnection.getNewConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.categoryDao = new CategoryDao();
        this.musicDao = new MusicDao();
        this.relationshipDao=new RelationshipDao();
        this.baseLink = baseLink;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (musicQueue.peek() != null) {
            String id = musicQueue.poll().get("id").toString();
            String resp = Utils.get(this.baseLink + id);
            parseSong(resp,id);
            int i = this.categoryDao.setDownload(conn, id);
            if (i>0){
                System.out.println("分类"+id+"添加完成");
            }
        }
    }

    //处理获取到的html并解析
    public void parseSong(String respData,String categoryId) {
        Pattern pattern = Pattern.compile("<a href=\"/song\\?id=[\\d]+\">[\\S]+</a>");
        Matcher matcher = pattern.matcher(respData);

        while (matcher.find()) {
            String group = matcher.group();
            Music music = strToMusic(group);

            Music music1 = this.musicDao.selectById(this.conn, music.getId());
            if (music1 == null) {
                this.musicDao.insert(this.conn, music);
                this.relationshipDao.insert(this.conn,music.getId(),categoryId);
            }
            try {
                this.conn.commit();
            } catch (SQLException e) {
                try {
                    this.conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    //把正则匹配出来的文本转Msic对象
    private Music strToMusic(String str) {
        Pattern pId = Pattern.compile("id=[\\d]+");
        Matcher matcher = pId.matcher(str);
        matcher.find();
        String group = matcher.group();
        String[] ids = group.split("=");
        String id = ids[1];
        String name = str.substring(str.indexOf(">") + 1, str.lastIndexOf("<"));
        return new Music(name, id);
    }
}
