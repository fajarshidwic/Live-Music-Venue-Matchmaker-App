package com.example.livemusicvenuematchmakerapp.dao;

import com.example.livemusicvenuematchmakerapp.DBUtil;
import com.example.livemusicvenuematchmakerapp.model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public static boolean addClient(Client client) {
        String query = "INSERT OR IGNORE INTO Clients (clientName) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, client.getClientName());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Clients";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                Client client = new Client();
                client.setClientId(rs.getInt("id"));
                client.setClientName(rs.getString("clientName"));
                clients.add(client);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}