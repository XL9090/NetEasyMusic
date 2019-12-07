package ltd.tongluren.dao;

import java.sql.Connection;
import java.util.Map;

public class RelationshipDao extends AbstractBaseDao {

    public void insert (Connection conn,String nameId,String categoryId){
        super.modify(conn,"insert into  relationship (music_id,categor_id) values (?,?)",nameId,categoryId);
    }

}
