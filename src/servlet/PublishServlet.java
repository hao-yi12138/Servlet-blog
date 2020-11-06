package servlet;

import model.Article;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-04 20:27
 **/
@MultipartConfig
@WebServlet("/publish")
public class PublishServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }
        req.setCharacterEncoding("utf-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        Part imagePart = req.getPart("image");
        InputStream is = imagePart.getInputStream();
        byte[] buffer = new byte[1024];
        try (OutputStream os = new FileOutputStream("E:\\github代码\\Servlet-blog\\保存上传过来的文件\\Hello.gif")) {
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    // 读到 EOS,所有数据全部读完了
                    break;
                }
                os.write(buffer, 0, len);
            }
            os.flush();
        }

        try {
            Article.publish(user.id, title, content);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect("/");
    }
}
