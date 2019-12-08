package ltd.tongluren.dao;

import ltd.tongluren.model.MusicCategory;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class CategoryDao extends AbstractBaseDao {

    public int insert(Connection conn, MusicCategory category) {
        return super.modify(conn, "insert into  music_category (`id`,`name`,`img_name`) values (?,?,?)", category.getId(), category.getName(), category.getImgName());
    }

    public MusicCategory selectById(Connection conn, String id) {
        List<Map<String, Object>> select = super.select(conn, "select `id`,`name`,`img_name` from music_category where id=?", id);
        if (select.size() < 1) {
            return null;
        }
        return new MusicCategory(
                select.get(0).get("id").toString(),
                select.get(0).get("name").toString(),
                select.get(0).get("img_name").toString()
        );
    }

    /**
     * @param conn jdbc connection
     * @param id need modify
     * @return
     */
    public int setDownload(Connection conn,String id){
        return super.modify(conn, "update  music_category set is_download=1 where id=?", id);
    }


    public List<Map<String, Object>> popNotDownload(Connection conn, Integer count){
        return  super.select(conn, "select id from music_category where is_download=0 limit ?", count);

    }

}
