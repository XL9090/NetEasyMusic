package ltd.tongluren.dao;

import ltd.tongluren.model.Music;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class MusicDao extends AbstractBaseDao {

    public Music selectById(Connection conn, String  id){
        List<Map<String, Object>> select = super.select(conn, "select * from music_name where id=?", id);
        if(select.size()<1){
            return null;
        }
        Map<String, Object> stringObjectMap = select.get(0);
        return new Music(stringObjectMap.get("name").toString(),stringObjectMap.get("id").toString());
    }

    public int insert(Connection conn,Music music){
        return  super.modify(conn,"insert into music_name (`id`,`name`) values (?,?)",music.getId(),music.getName());
    }

    public int deleteById (Connection conn,String id){
        return super.modify(conn,"delete from music_name where id=?",id);
    }

    public List<Map<String,Object>> pop(Connection conn,Integer count){
        return super.select(conn,"select id from music_name where is_download=0 limit ?",count);
    }

    public int setDownload(Connection conn,String id){
        return super.modify(conn,"update   music_name set is_download=1 where id=?",id);
    }
}
