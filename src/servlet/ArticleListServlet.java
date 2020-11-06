package servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-05 18:50
 **/
@WebServlet("/api/article-list.json")
public class ArticleListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取文章列表
        List<Article> articleList = null;
        try {
            articleList = Article.list();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        // 2. 将文章列表转换成 JSON 字符串
        String jsonText = translateToJSON(articleList);
        // 3. 写入 response
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().println(jsonText);
    }

    private String translateToJSON(List<Article> articleList) {
        JSONArray array = new JSONArray();
        for (Article a:articleList) {
            JSONObject authorObject = new JSONObject();
            JSONObject articleObject = new JSONObject();
            authorObject.put("id", a.authorId);
            authorObject.put("username", a.authorUsername);

            articleObject.put("id", a.id);
            articleObject.put("author", authorObject);
            articleObject.put("title", a.title);
            articleObject.put("published-at", a.publishedAt);

            array.add(articleObject);
        }
        return array.toJSONString();
    }
}
