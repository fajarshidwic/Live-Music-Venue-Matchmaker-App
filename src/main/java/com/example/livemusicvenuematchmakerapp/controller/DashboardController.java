package com.example.livemusicvenuematchmakerapp.controller;

import com.example.livemusicvenuematchmakerapp.dao.*;
import com.example.livemusicvenuematchmakerapp.model.*;

import java.util.List;

public class DashboardController {
    public List<Venue> getAllVenues() {
        return VenueDAO.getAllVenues();
    }

    public boolean addVenue(Venue venue) {
        return VenueDAO.addVenue(venue);
    }

    public Venue searchVenueByName(String name) {
        return VenueDAO.getVenueByName(name);
    }

    public boolean createStaffAccount(User user) {
        return UserDAO.createStaffUser(user);
    }

    public List<User> getAllUsers() { return UserDAO.getAllUsers(); }

    public boolean updateUser(User user) { return UserDAO.updateUser(user); }

    public boolean deleteUser(String username) { return UserDAO.deleteUser(username); }

    public boolean upgradeUser(String username) { return UserDAO.upgradeUser(username); }

    public boolean updateProfile(User user) { return UserDAO.updateProfile(user); }

    public List<Request> getAllRequests() { return RequestDAO.getAllRequests(); }

    public List<Event> getAllEvents() {
        return EventDAO.getAllEvents();
    }
    public List<Booking> getAllBookings() {
        return BookingDAO.getAllBookings();
    }
    public List<Order> getAllOrders() {
        return OrderDAO.getAllOrders();
    }
    public List<Client> getAllClients() {
        return ClientDAO.getAllClients();
    }
}
