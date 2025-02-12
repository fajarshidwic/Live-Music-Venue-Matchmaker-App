package com.example.livemusicvenuematchmakerapp.dao;

import com.example.livemusicvenuematchmakerapp.DBUtil;
import com.example.livemusicvenuematchmakerapp.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static boolean addBooking(Booking booking) {
        String query = "INSERT OR IGNORE INTO Bookings (eventId, venueName, date, time, duration) VALUES (?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, booking.getEventId());
            pstmt.setString(2, booking.getVenueName());
            pstmt.setString(3, booking.getDate());
            pstmt.setString(4, booking.getTime());
            pstmt.setInt(5, booking.getDuration());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        booking.setBookingId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("id"));
                booking.setEventId(rs.getInt("eventId"));
                booking.setVenueName(rs.getString("venueName"));
                booking.setDate(rs.getString("date"));
                booking.setTime(rs.getString("time"));
                booking.setDuration(rs.getInt("duration"));
                bookings.add(booking);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static Booking getBookingByEventId(int eventId) {
        String query = "SELECT * FROM Bookings WHERE eventId = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("id"));
                booking.setEventId(rs.getInt("eventId"));
                booking.setVenueName(rs.getString("venueName"));
                booking.setDate(rs.getString("date"));
                booking.setTime(rs.getString("time"));
                booking.setDuration(rs.getInt("duration"));
                return booking;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateBooking(Booking booking) {
        String query = "UPDATE Bookings SET venueName = ?, date = ?, time = ?, duration = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, booking.getVenueName());
            pstmt.setString(2, booking.getDate());
            pstmt.setString(3, booking.getTime());
            pstmt.setInt(4, booking.getDuration());
            pstmt.setInt(5, booking.getBookingId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void clearBookings() {
        String query = "DELETE FROM Bookings";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
