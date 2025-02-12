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
            stmt.execute("DROP TABLE IF EXISTS Venues");
            stmt.execute("DROP TABLE IF EXISTS Requests");
            stmt.execute("DROP TABLE IF EXISTS Events");
            stmt.execute("DROP TABLE IF EXISTS Bookings");
            stmt.execute("DROP TABLE IF EXISTS Orders");
            stmt.execute("DROP TABLE IF EXISTS Clients");

            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "firstName TEXT NOT NULL, " +
                    "lastName TEXT NOT NULL, " +
                    "role TEXT NOT NULL" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Venues (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "capacity INTEGER, " +
                    "suitableFor TEXT, " +
                    "category TEXT, " +
                    "venueType TEXT, " +
                    "bookingPricePerHour REAL" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Requests (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "client TEXT, " +
                    "title TEXT, " +
                    "artist TEXT, " +
                    "date TEXT, " +
                    "time TEXT, " +
                    "duration INTEGER, " +
                    "targetAudience INTEGER, " +
                    "type TEXT, " +
                    "category TEXT, " +
                    "UNIQUE(client, title)" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Events (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "requestId INTEGER, " +
                    "title TEXT, " +
                    "mainArtist TEXT, " +
                    "date TEXT, " +
                    "time TEXT, " +
                    "venue TEXT" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Bookings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "eventId INTEGER, " +
                    "venueName TEXT, " +
                    "date TEXT, " +
                    "time TEXT, " +
                    "duration INTEGER" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "bookingId INTEGER UNIQUE, " +
                    "commission REAL, " +
                    "total REAL" +
                    ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Clients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "clientName TEXT UNIQUE" +
                    ");");

            stmt.execute("INSERT OR IGNORE INTO Users (username, password, firstName, lastName, role) " +
                    "VALUES ('manager', 'manager', 'Default', 'Manager', 'manager');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
