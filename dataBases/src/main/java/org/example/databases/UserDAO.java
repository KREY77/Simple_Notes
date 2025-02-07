package org.example.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    static User getByUsername(String username){
        String request = "SELECT * FROM users WHERE username = ?";
        try(Connection con = DBManager.getConnection()){
            PreparedStatement statement = con.prepareStatement(request);
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                User user = new User(resultSet.getLong("id"), resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("email"),resultSet.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    static boolean addUser(User user){
        String addUser = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try(Connection con = DBManager.getConnection()){
            PreparedStatement statement = con.prepareStatement(addUser);
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getEmail());
            statement.setString(4,user.getRole());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
