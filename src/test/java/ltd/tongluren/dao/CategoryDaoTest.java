package ltd.tongluren.dao;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {
    CategoryDao categoryDao=new CategoryDao();

    @Test
    void insert() {
    }

    @Test
    void selectById() {
    }

    @Test
    void setDownload() {
    }

    @Test
    void popNotDownload() {
        List<Map<String, Object>> maps = categoryDao.popNotDownload(DBConnection.getNewConnection(), 10);
        System.out.println(maps);
    }
}