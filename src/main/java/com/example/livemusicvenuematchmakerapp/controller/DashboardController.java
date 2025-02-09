package com.example.livemusicvenuematchmakerapp.controller;

import com.example.livemusicvenuematchmakerapp.dao.UserDAO;
import com.example.livemusicvenuematchmakerapp.dao.VenueDAO;
import com.example.livemusicvenuematchmakerapp.model.User;
import com.example.livemusicvenuematchmakerapp.model.Venue;
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
}
