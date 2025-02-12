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

    public void autoMatch() {
        List<Request> requests = RequestDAO.getAllRequests();
        List<Venue> venues = VenueDAO.getAllVenues();

        for (Request req : requests) {
            Event event = EventDAO.getEventByRequestIdOrTitle(req.getId(), req.getTitle());
            Venue bestMatch = null;

            for (Venue v : venues) {
                if (v.getCapacity() >= req.getTargetAudience() &&
                        v.getSuitableFor().equalsIgnoreCase(req.getType()) &&
                        v.getVenueType().equalsIgnoreCase(req.getCategory())) {
                    bestMatch = v;
                    break;
                }
            }
            if (bestMatch == null) {
                for (Venue v : venues) {
                    if (v.getCapacity() >= req.getTargetAudience()) {
                        bestMatch = v;
                        break;
                    }
                }
            }

            if (bestMatch != null) {
                if (event == null) {
                    event = new Event();
                    event.setRequestId(req.getId());
                    event.setTitle(req.getTitle());
                    event.setMainArtist(req.getArtist());
                    event.setDate(req.getDate());
                    event.setTime(req.getTime());
                    event.setVenue(bestMatch.getName());
                    EventDAO.addEvent(event);
                } else {
                    if (event.getRequestId() == 0) {
                        event.setRequestId(req.getId());
                    }
                    event.setTitle(req.getTitle());
                    event.setMainArtist(req.getArtist());
                    event.setDate(req.getDate());
                    event.setTime(req.getTime());
                    event.setVenue(bestMatch.getName());
                    EventDAO.updateEvent(event);
                }

                Booking booking = BookingDAO.getBookingByEventId(event.getEventId());
                if (booking == null) {
                    booking = new Booking();
                    booking.setEventId(event.getEventId());
                    booking.setVenueName(bestMatch.getName());
                    booking.setDate(req.getDate());
                    booking.setTime(req.getTime());
                    booking.setDuration(req.getDuration());
                    BookingDAO.addBooking(booking);
                } else {
                    booking.setVenueName(bestMatch.getName());
                    booking.setDate(req.getDate());
                    booking.setTime(req.getTime());
                    booking.setDuration(req.getDuration());
                    BookingDAO.updateBooking(booking);
                }

                double cost = bestMatch.getBookingPricePerHour() * req.getDuration();
                int count = 0;
                for (Request r : requests) {
                    if (r.getClient().equalsIgnoreCase(req.getClient())) {
                        count++;
                    }
                }
                double commissionRate = (count > 1) ? 0.09 : 0.10;
                double commission = cost * commissionRate;

                Order order = OrderDAO.getOrderByBookingId(booking.getBookingId());
                if (order == null) {
                    order = new Order();
                    order.setBookingId(booking.getBookingId());
                    order.setCommission(commission);
                    order.setTotal(cost);
                    OrderDAO.addOrder(order);
                } else {
                    order.setCommission(commission);
                    order.setTotal(cost);
                    OrderDAO.updateOrder(order);
                }

                Client client = new Client();
                client.setClientName(req.getClient());
                ClientDAO.addClient(client);
            }
        }
    }
}
