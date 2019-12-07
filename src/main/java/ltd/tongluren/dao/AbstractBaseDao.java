package ltd.tongluren.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public  abstract class AbstractBaseDao implements BaseDao {

    @Override
    public List<Map<String, Object>> select(Connection conn,String sql, Object... parms) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=conn.prepareStatement(sql);
            for(int i=0;i<parms.length;i++){
                ps.setObject(i,parms[i]);
            }
            rs=ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            List<Map<String,Object>> retuenList=new ArrayList<>();
            while (rs.next()){
                int index=1;
                String name=rsmd.getColumnName(index);
                Map<String,Object> item=new HashMap<>();
                while (!"".equals(name)){
                    item.put(name,rs.getObject(name));
                }
                retuenList.add(item);
            }
            return retuenList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(ps,rs);
        }
        return null;
    }

    @Override
    public int modify(Connection conn,String sql,Object... parms) {
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            for(int i=1;i<=parms.length;i++){
                ps.setObject(i,parms[i-1]);
            }
            boolean execute = ps.execute();
            return ps.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(ps);
        }
        return -1;
    }
}
