package com.example.livemusicvenuematchmakerapp.dao;

import com.example.livemusicvenuematchmakerapp.DBUtil;
import com.example.livemusicvenuematchmakerapp.model.Venue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDAO {
    public static boolean addVenue(Venue venue) {
        String query = "INSERT INTO Venues (name, capacity, suitableFor, category, bookingPricePerHour) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, venue.getName());
            pstmt.setInt(2, venue.getCapacity());
            pstmt.setString(3, venue.getSuitableFor());
            pstmt.setString(4, venue.getCategory());
            pstmt.setDouble(5, venue.getBookingPricePerHour());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Venue> getAllVenues() {
        List<Venue> venues = new ArrayList<>();
        String query = "SELECT * FROM Venues";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                Venue venue = new Venue();
                venue.setId(rs.getInt("id"));
                venue.setName(rs.getString("name"));
                venue.setCapacity(rs.getInt("capacity"));
                venue.setSuitableFor(rs.getString("suitableFor"));
                venue.setCategory(rs.getString("category"));
                venue.setBookingPricePerHour(rs.getDouble("bookingPricePerHour"));
                venues.add(venue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }

    public static Venue getVenueByName(String name) {
        String query = "SELECT * FROM Venues WHERE name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Venue venue = new Venue();
                venue.setId(rs.getInt("id"));
                venue.setName(rs.getString("name"));
                venue.setCapacity(rs.getInt("capacity"));
                venue.setSuitableFor(rs.getString("suitableFor"));
                venue.setCategory(rs.getString("category"));
                venue.setBookingPricePerHour(rs.getDouble("bookingPricePerHour"));
                return venue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
