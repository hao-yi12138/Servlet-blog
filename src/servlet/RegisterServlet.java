package servlet;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-11-04 17:24
 **/
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user;
        try{
              user = User.register(username,password);
        }catch (SQLException e){
            throw new ServletException(e);
        }
        if(user == null){
            resp.sendRedirect("register.html");
            return;
        }
        // 注册完成后视为登录
        HttpSession session = req.getSession();
        session.setAttribute("user",user);
        resp.sendRedirect("/");
        // 注册完成后还需要人工进行登录
        /*
        resp.sendRedirect("login.html");
        */
    }
}
