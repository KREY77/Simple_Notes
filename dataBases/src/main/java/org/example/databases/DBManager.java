package org.example.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    public static final String url = "jdbc:postgresql://localhost:5432/UsersDB";
    public static final String name = "postgres";
    public static final String password = "GG_JDH";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,name,password);
    }
}
