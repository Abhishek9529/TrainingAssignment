package com.mednet.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnection {

    // Load database.properties file
    private static final ResourceBundle rb = ResourceBundle.getBundle("database");

    // Read values from database.properties
    private static final String DRIVER = rb.getString("db.driver");
    private static final String URL = rb.getString("db.url");
    private static final String USERNAME = rb.getString("db.username");
    private static final String PASSWORD = rb.getString("db.password");

    // Method to return database connection
    public static Connection getConnection() {

        Connection con = null;

        try {
            // Load JDBC Driver
            Class.forName(DRIVER);

            // Create Connection
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}