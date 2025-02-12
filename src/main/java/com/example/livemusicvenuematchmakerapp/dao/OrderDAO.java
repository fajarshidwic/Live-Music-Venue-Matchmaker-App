package com.example.livemusicvenuematchmakerapp.dao;

import com.example.livemusicvenuematchmakerapp.DBUtil;
import com.example.livemusicvenuematchmakerapp.model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static boolean addOrder(Order order) {
        String query = "INSERT OR IGNORE INTO Orders (bookingId, commission, total) VALUES (?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, order.getBookingId());
            pstmt.setDouble(2, order.getCommission());
            pstmt.setDouble(3, order.getTotal());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                Order order = new Order();
                order.setOrderId(rs.getInt("id"));
                order.setBookingId(rs.getInt("bookingId"));
                order.setCommission(rs.getDouble("commission"));
                order.setTotal(rs.getDouble("total"));
                orders.add(order);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
