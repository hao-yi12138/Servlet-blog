package model;

import com.sun.java.browser.plugin2.liveconnect.v1.Result;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-04 17:26
 **/
public class Article {
    public int authorId;
    public String authorUsername;
    public int id;
    public String title;
    public String publishedAt;
    public String content;
    /*
    这么用 DateFormat 是错误的，因为是
    1. DateFormat 不是线程安全的
    2. 我们的 publish 实际上是运行在多线程环境下的

    static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void publish(int userId, String title, String content) {
        Date date = new Date();
        String publishedAt = format.format(date); // 2020-07-12 11:42:38
    }
    */
         public static void publish(int userId,String title,String content) throws SQLException {
             DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             Date date = new Date();
             String publishedAt = format.format(date);
             try(Connection connection = DBUtil.getConnection()){
                 String sql = "insert into articles (user_id, title, content, published_at) values (?,?,?,?)";
                try(PreparedStatement s = connection.prepareStatement(sql)){
                    s.setInt(1,userId);
                    s.setString(2, title);
                    s.setString(3, content);
                    s.setString(4, publishedAt);
                    s.executeUpdate();
                }
             }
         }

    public static List<Article> list() throws SQLException {
        List<Article> articleList = new ArrayList<>();
        try(Connection c = DBUtil.getConnection()){
            String sql = "select users.id,username,articles.id,title,published_at " +
                    "from users join articles on users.id = articles.user_id " +
                    "order by published_at desc ";
            try(PreparedStatement s = c.prepareStatement(sql)){
                try(ResultSet r = s.executeQuery()){
                    while(r.next()){
                        Article article = new Article();
                        article.id = r.getInt("articles.id");
                        article.authorId = r.getInt("users.id");
                        article.authorUsername = r.getString("username");
                        article.title = r.getString("title");
                        article.publishedAt = r.getString("published_at");

                        articleList.add(article);
                    }
                }
            }
        }
        return articleList;
    }

    public static Article get(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "select title, content from articles where id = ?";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, id);
                try (ResultSet r = s.executeQuery()) {
                    if (!r.next()) {
                        return null;
                    }

                    Article article = new Article();
                    article.title = r.getString("title");
                    article.content = r.getString("content");

                    return article;
                }
            }
        }
    }
}
