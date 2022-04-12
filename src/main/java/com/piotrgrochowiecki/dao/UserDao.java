package com.piotrgrochowiecki.dao;

import com.piotrgrochowiecki.exception.DaoException;
import com.piotrgrochowiecki.model.User;
import com.piotrgrochowiecki.utils.DbUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

//    private static final String DROP_DATABASE_QUERY = "DROP DATABASE IF EXISTS ";
//    private static final String CREATE_DATABASE_QUERY = "CREATE DATABASE IF NOT EXISTS " + DATABASE + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
//    private static final String DATABASE = "workshop2";
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users(\n" +
            "    id INT(11) AUTO_INCREMENT NOT NULL,\n" +
            "    email VARCHAR(255) NOT NULL UNIQUE,\n" +
            "    username VARCHAR(255) NOT NULL UNIQUE,\n" +
            "    password VARCHAR(60) NOT NULL,\n" +
            "    PRIMARY KEY (id)\n" +
            ");";

    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE users SET username = ?, email = ? WHERE id = ?";
    private static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE users SET password = ? WHERE id = ?";
    private static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String DELETE_ALL_USERS_QUERY = "DELETE FROM users";

    public User create(User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while creating new user", e);
        }
    }

    public String hashPassword(User user) {
        return BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    }

    public void updatePassword(User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_PASSWORD_QUERY);
            statement.setString(1, hashPassword(user));
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error!", e);
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_BY_ID_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while reading the user", e);
        }
        return null;
    }

    public void update(User user){
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_BY_ID_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while updating the user", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_BY_ID_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("User with id " + id + " has just been deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while deleting the user", e);
        }
    }

    public void deleteAll() {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ALL_USERS_QUERY);
            statement.executeUpdate();
            System.out.println("All users have just been deleted from database!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while deleting all users", e);
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public User[] findAll() {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            User[] users = new User[0];
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                User user = read(userId);
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while finding all users", e);
        }
    }

//    public void dropDatabase() {
//        try (Connection conn = DbUtil.connect()) {
//            PreparedStatement statement = conn.prepareStatement(DROP_DATABASE_QUERY + DATABASE);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DaoException("Error while dropping a database", e);
//        }
//    }
//
//    public void createDatabase() {
//        try (Connection conn = DbUtil.connect()) {
//            PreparedStatement statement = conn.prepareStatement(CREATE_DATABASE_QUERY);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DaoException("Error while creating a database", e);
//        }
//    }

    public void createUsersTable() {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USERS_TABLE);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error while creating a table", e);
        }
    }

}
