package com.example.livemusicvenuematchmakerapp.dao;

import com.example.livemusicvenuematchmakerapp.DBUtil;
import com.example.livemusicvenuematchmakerapp.model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {

    public static boolean addRequest(Request request) {
        String query = "INSERT OR IGNORE INTO Requests (client, title, artist, date, time, duration, targetAudience, type, category) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, request.getClient());
            pstmt.setString(2, request.getTitle());
            pstmt.setString(3, request.getArtist());
            pstmt.setString(4, request.getDate());
            pstmt.setString(5, request.getTime());
            pstmt.setInt(6, request.getDuration());
            pstmt.setInt(7, request.getTargetAudience());
            pstmt.setString(8, request.getType());
            pstmt.setString(9, request.getCategory());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        request.setId(rs.getInt(1));
                    }
                }
            } else {
                String selectQuery = "SELECT id FROM Requests WHERE client = ? AND title = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    selectStmt.setString(1, request.getClient());
                    selectStmt.setString(2, request.getTitle());
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            request.setId(rs.getInt("id"));
                            rowsAffected = 1;
                        }
                    }
                }
            }
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT * FROM Requests";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setClient(rs.getString("client"));
                request.setTitle(rs.getString("title"));
                request.setArtist(rs.getString("artist"));
                request.setDate(rs.getString("date"));
                request.setTime(rs.getString("time"));
                request.setDuration(rs.getInt("duration"));
                request.setTargetAudience(rs.getInt("targetAudience"));
                request.setType(rs.getString("type"));
                request.setCategory(rs.getString("category"));
                requests.add(request);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}
