package com.example.livemusicvenuematchmakerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String DB_URL = "jdbc:sqlite:lmvmapp.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "firstName TEXT NOT NULL, " +
                    "lastName TEXT NOT NULL, " +
                    "role TEXT NOT NULL" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Venues (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "capacity INTEGER, " +
                    "suitableFor TEXT, " +
                    "category TEXT, " +
                    "bookingPricePerHour REAL" +
                    ");");

            stmt.execute("INSERT OR IGNORE INTO Users (username, password, firstName, lastName, role) " +
                    "VALUES ('manager', 'manager', 'Default', 'Manager', 'manager');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
