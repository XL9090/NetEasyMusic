package ltd.tongluren.Core;


import ltd.tongluren.dao.CategoryDao;
import ltd.tongluren.dao.DBConnection;
import ltd.tongluren.model.MusicCategory;

import javax.rmi.CORBA.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseLink implements Runnable {
    Connection conn= DBConnection.getNewConnection();

    @Override
    public void run() {
        int start = 0;
        while (true) {
            String pageHTML = Utils.get(Utils.BASE_URL + start);
            if (parseLinks(pageHTML)) {
                System.err.println(start);
                start += 35;
            } else {
                return;
            }
        }
    }

    private boolean parseLinks(String rootStr) {
        Pattern pattern = Pattern.compile("<img class=\"j-flag\" src=\"[\\S]+\"/>\n" +
                "<a title=\"[\\S]+\" href=\"/playlist\\?id=[\\d]+\" class=\"msk\"></a>");
        Matcher matcher = pattern.matcher(rootStr);
        boolean b = false;
        CategoryDao categoryDao = new CategoryDao();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (matcher.find()) {
            b = true;
            String group = matcher.group();
            System.out.println(group);
            MusicCategory musicCategory = parseToMudicCategory(group);
            if(null==categoryDao.selectById(conn,musicCategory.getId())){
                categoryDao.insert(conn,musicCategory);
            }
            try {
                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return b;
    }

    /**
     * spend reg matcher str to MusicCategory
     * @param str
     * @return
     */
    private MusicCategory parseToMudicCategory(String str) {
        Pattern pImg = Pattern.compile("http://[\\S]+\\?param=140y140");
        Matcher maImg = pImg.matcher(str);
        MusicCategory musicCategory = new MusicCategory();
        if (maImg.find()) {
            String img = maImg.group();
            musicCategory.setImgName(img);
        }
        Pattern pName = Pattern.compile("title=\"[\\S]+\"");
        Matcher maName = pName.matcher(str);
        if (maName.find()) {
            String name = maName.group();
            name = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\""));
            musicCategory.setName(name);
        }
        Pattern pId = Pattern.compile("id=[\\d]+");
        Matcher maId = pId.matcher(str);
        if (maId.find()) {
            String id = maId.group();
            String[] split = id.split("=");
            id = split[1];
            musicCategory.setId(id);
        }

        return musicCategory;
    }
}
