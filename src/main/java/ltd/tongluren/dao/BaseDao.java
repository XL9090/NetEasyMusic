package ltd.tongluren.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface BaseDao {


    public List<Map<String,Object>> select(Connection conn, String sql, Object... parms);

    public int modify(Connection conn,String sql,Object... parms);


}
