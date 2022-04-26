package com.piotrgrochowiecki.web;

import com.piotrgrochowiecki.dao.UserDao;
import com.piotrgrochowiecki.model.User;
import com.piotrgrochowiecki.utils.DbUtil;
import com.sun.net.httpserver.HttpsServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/user/show")
public class UserDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DbUtil.connect()) {
            int id = Integer.parseInt(req.getParameter("id"));
            UserDao userDao = new UserDao();
            User user = userDao.read(id);

            req.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/users/details.jsp").forward(req,resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}