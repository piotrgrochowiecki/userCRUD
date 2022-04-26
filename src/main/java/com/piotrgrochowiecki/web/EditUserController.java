package com.piotrgrochowiecki.web;

import com.piotrgrochowiecki.dao.UserDao;
import com.piotrgrochowiecki.model.User;
import com.piotrgrochowiecki.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/user/edit")
public class EditUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DbUtil.connect()){
            int id = Integer.parseInt(req.getParameter("id"));
            UserDao userDao = new UserDao();
            User user = userDao.read(id);
            req.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/users/edit.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DbUtil.connect()) {
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.setContentType("text/html;charset=utf8");
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());

            int id = Integer.parseInt(req.getParameter("id"));
            String userName = req.getParameter("userName");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User user = new User();
            user.setId(id);
            user.setUserName(userName);
            user.setEmail(email);
            user.setPassword(password);

            UserDao userDao = new UserDao();
            userDao.updatePassword(user);
            userDao.update(user);

            resp.sendRedirect("/users/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}