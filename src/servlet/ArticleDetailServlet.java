package servlet;

import model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-06 16:02
 **/
@WebServlet("/article")
public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);

        Article article = null;
        try {
            article = Article.get(id);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (article == null) {
            resp.sendError(404, "没有这篇文章");
            return;
        }

        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<h1>" + article.title + "</h1>");
        writer.println("<p>" + article.content + "</p>");
    }
}
