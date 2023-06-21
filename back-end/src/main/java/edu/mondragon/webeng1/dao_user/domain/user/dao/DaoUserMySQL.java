package edu.mondragon.webeng1.dao_user.domain.user.dao;

import java.util.ArrayList;

import edu.mondragon.webeng1.dao_user.config.MySQLConfig;
import edu.mondragon.webeng1.dao_user.domain.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUserMySQL implements DaoUser {

    private MySQLConfig mysqlConfig;

    public DaoUserMySQL() {
        mysqlConfig = MySQLConfig.getInstance();
    }

    @Override
    public void insertUser(User user) {
        System.out.println("insertUser not implemented yet.");
    }

    @Override
    public User loadUser(String username, String password) {
        String sqlQuery = "SELECT * FROM user WHERE username=? AND password=?";
        User user = null;
        Connection connection = mysqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, username);
            stm.setString(2, password);
            System.out.println(stm);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setSecondName(rs.getString("second_name"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error DaoLoginMysql loadUser " + username);
        }
        mysqlConfig.disconnect(connection, stm);
        return user;
    }

    @Override
    public User loadUser(int userId) {
        System.out.println("loadUser not implemented yet.");
        return null;
    }

    @Override
    public ArrayList<User> loadUsers() {
        System.out.println("loadAllUsers not implemented yet.");
        return null;
    }

}
