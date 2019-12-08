package ltd.tongluren.Core;

import ltd.tongluren.dao.CategoryDao;
import ltd.tongluren.dao.DBConnection;
import ltd.tongluren.dao.MusicDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParseMusicTest {
    CategoryDao categoryDao=new CategoryDao();
    ConcurrentLinkedQueue<Map<String,Object>> musicQueue=new ConcurrentLinkedQueue<>();

    @Test
    public  void test(){

    }

}