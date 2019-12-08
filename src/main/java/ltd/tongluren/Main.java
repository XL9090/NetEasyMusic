package ltd.tongluren;


import ltd.tongluren.Core.Handle;
import ltd.tongluren.Core.ParseLink;
import ltd.tongluren.Core.Utils;
import ltd.tongluren.dao.DBConnection;
import ltd.tongluren.model.Music;
import org.junit.Test;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    /**
     * 连接数据库把数据存储起来，
     * 验证歌曲唯一，不重复
     * 多次爬取分类页面
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        new Handle().run();
    }
}
