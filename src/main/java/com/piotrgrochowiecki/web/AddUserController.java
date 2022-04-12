package com.piotrgrochowiecki.web;

import com.piotrgrochowiecki.dao.UserDao;
import com.piotrgrochowiecki.exception.DaoException;
import com.piotrgrochowiecki.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

@WebServlet("/users/add")
public class AddUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html;charset=utf8");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repeatedPassword = req.getParameter("repeatedPassword");

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        try {
            UserDao userDao = new UserDao();
            userDao.create(user);
            resp.sendRedirect("/users/list");
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
