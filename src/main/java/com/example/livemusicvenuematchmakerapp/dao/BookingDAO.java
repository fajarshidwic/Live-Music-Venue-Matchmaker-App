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
            return rowsAffected > 0;
        } catch(SQLException e) {
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
}
