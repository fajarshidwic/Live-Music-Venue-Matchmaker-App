package com.example.livemusicvenuematchmakerapp.util;

import com.example.livemusicvenuematchmakerapp.dao.*;
import com.example.livemusicvenuematchmakerapp.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVImporter {

    public static void importVenues(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                String[] fields = line.split("\\s*,\\s*");
                if (fields.length >= 6) {
                    String name = fields[0].trim();
                    int capacity = Integer.parseInt(fields[1].trim());
                    String suitableFor = fields[2].trim();
                    String category = fields[3].trim();
                    String venueType = fields[4].trim();
                    double bookingPricePerHour = Double.parseDouble(fields[5].trim());

                    Venue venue = new Venue();
                    venue.setName(name);
                    venue.setCapacity(capacity);
                    venue.setSuitableFor(suitableFor);
                    venue.setCategory(category);
                    venue.setVenueType(venueType);
                    venue.setBookingPricePerHour(bookingPricePerHour);

                    VenueDAO.addVenue(venue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void importRequests(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                String[] fields = line.split("\\s*,\\s*");
                if (fields.length >= 9) {
                    String clientName = fields[0].trim();
                    String title = fields[1].trim();
                    String artist = fields[2].trim();
                    String date = fields[3].trim();
                    String time = fields[4].trim();
                    int duration = Integer.parseInt(fields[5].trim());
                    int targetAudience = Integer.parseInt(fields[6].trim());
                    String type = fields[7].trim();
                    String category = fields[8].trim();

                    Request request = new Request();
                    request.setClient(clientName);
                    request.setTitle(title);
                    request.setArtist(artist);
                    request.setDate(date);
                    request.setTime(time);
                    request.setDuration(duration);
                    request.setTargetAudience(targetAudience);
                    request.setType(type);
                    request.setCategory(category);
                    RequestDAO.addRequest(request);


                } else {
                    System.out.println("Unexpected number of fields in requests CSV: " + fields.length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
