package com.example.livemusicvenuematchmakerapp.dao;

import com.example.livemusicvenuematchmakerapp.DBUtil;
import com.example.livemusicvenuematchmakerapp.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public static boolean addEvent(Event event) {
        String query = "INSERT OR IGNORE INTO Events (requestId, title, mainArtist, date, time, venue) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, event.getRequestId());
            pstmt.setString(2, event.getTitle());
            pstmt.setString(3, event.getMainArtist());
            pstmt.setString(4, event.getDate());
            pstmt.setString(5, event.getTime());
            pstmt.setString(6, event.getVenue());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        event.setEventId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM Events";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                Event event = new Event();
                event.setEventId(rs.getInt("id"));
                event.setRequestId(rs.getInt("requestId"));
                event.setTitle(rs.getString("title"));
                event.setMainArtist(rs.getString("mainArtist"));
                event.setDate(rs.getString("date"));
                event.setTime(rs.getString("time"));
                event.setVenue(rs.getString("venue"));
                events.add(event);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static boolean updateEvent(Event event) {
        String query = "UPDATE Events SET title = ?, mainArtist = ?, date = ?, time = ?, venue = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getMainArtist());
            pstmt.setString(3, event.getDate());
            pstmt.setString(4, event.getTime());
            pstmt.setString(5, event.getVenue());
            pstmt.setInt(6, event.getEventId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Event getEventByRequestIdOrTitle(int requestId, String title) {
        String query = "SELECT * FROM Events WHERE requestId = ? OR (requestId = 0 AND title = ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, requestId);
            pstmt.setString(2, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("id"));
                event.setRequestId(rs.getInt("requestId"));
                event.setTitle(rs.getString("title"));
                event.setMainArtist(rs.getString("mainArtist"));
                event.setDate(rs.getString("date"));
                event.setTime(rs.getString("time"));
                event.setVenue(rs.getString("venue"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
