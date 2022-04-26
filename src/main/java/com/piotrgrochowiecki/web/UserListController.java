package com.piotrgrochowiecki.web;

import com.piotrgrochowiecki.model.User;
import com.piotrgrochowiecki.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/list")
public class UserListController extends HttpServlet {

    private static final String FIND_ALL_USERS_QUERY = "SELECT id, email, username FROM users;";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = DbUtil.connect()) {
            PreparedStatement ps = connection.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList<>();

            while (rs.next()) {
                users.add(new User(Integer.parseInt(rs.getString("id")), rs.getString("username"), rs.getString("email")));
            }
            req.setAttribute("users", users);

            getServletContext().getRequestDispatcher("/users/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
