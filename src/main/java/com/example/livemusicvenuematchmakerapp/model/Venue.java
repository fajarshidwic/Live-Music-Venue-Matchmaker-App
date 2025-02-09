package com.example.livemusicvenuematchmakerapp.model;

public class Venue {
    private int id;
    private String name;
    private int capacity;
    private String suitableFor;
    private String category;
    private double bookingPricePerHour;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getSuitableFor() { return suitableFor; }
    public void setSuitableFor(String suitableFor) { this.suitableFor = suitableFor; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getBookingPricePerHour() { return bookingPricePerHour; }
    public void setBookingPricePerHour(double bookingPricePerHour) { this.bookingPricePerHour = bookingPricePerHour; }
}
