package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String DB_URL = "jdbc:postgresql://localhost/passwordhasher";
    private static final String USER = "java";
    private static final String PASSWORD = "123";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to: " + conn.getMetaData().getURL());
            return conn;

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;

        }
    }
}

