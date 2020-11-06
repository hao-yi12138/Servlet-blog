package servlet;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-04 20:27
 **/
@WebServlet("/publish.html")
public class PublishHtmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            resp.sendRedirect("/login.html");
            return;
        }
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>博客|发表文章</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "        <form method=\"post\" action=\"publish\" enctype=\"multipart/form-data\">\n" +
                "            <div>\n" +
                "                <label for=\"title\">标题</label>\n" +
                "                <input type=\"text\" id=\"title\" name=\"title\">\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <label for=\"content\">正文</label>\n" +
                "                <textarea id=\"content\" name=\"content\"></textarea>\n" +
                "            </div>\n" +
                "        <div>\n" +
                "            <label for=\"image\">图片</label>\n" +
                "            <input type=\"file\" id=\"image\" name=\"image\">" +
                "        </div>\n" +
                "        \n" +
                "            <div>\n" +
                "                <button type=\"submit\">发表</button>\n" +
                "            </div>\n" +
                "        </form>\n" +
                "</body>\n" +
                "</html>";
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(htmlContent);
    }
}
