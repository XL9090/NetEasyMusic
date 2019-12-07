package ltd.tongluren.dao;

import ltd.tongluren.model.Music;
import ltd.tongluren.model.MusicCategory;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class CategoryDao extends  AbstractBaseDao {

    public int insert(Connection conn, MusicCategory category){
        return  super.modify(conn,"insert into  music_category (`id`,`name`,`img_name`) values (?,?,?)",category.getId(),category.getName(),category.getImgName());
    }

    public MusicCategory selectById(Connection conn,String id){
        List<Map<String, Object>> select = super.select(conn, "select `id`,`name`,`img_name` from music_category where id=?", id);
        return new MusicCategory(
                select.get(0).get("id").toString(),
                select.get(0).get("name").toString(),
                select.get(0).get("img_name").toString()
        );
    }

    @Test
    public void test(){
        System.out.println(insert(DBConnection.getConnection(),new MusicCategory("1","11","1")));
    }
}
