package com.example.livemusicvenuematchmakerapp.model;

public class Event {
    private int eventId;
    private int requestId;
    private String title;
    private String mainArtist;
    private String date;
    private String time;
    private String venue;

    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMainArtist() {
        return mainArtist;
    }
    public void setMainArtist(String mainArtist) {
        this.mainArtist = mainArtist;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
}
