package com.example.bookingplane.flight;

import java.time.LocalDate;

public class Flight {
    private Long id;
    private String destination;
    private LocalDate departureDate;
    private String flightDuration;
    private int price;

    public Flight() {
    }

    public Flight(Long id, String destination, LocalDate departureDate, String flightDuration, int price) {
        this.id = id;
        this.destination = destination;
        this.departureDate = departureDate;
        this.flightDuration = flightDuration;
        this.price = price;
    }

    public Flight(String destination, LocalDate departureDate, String flightDuration, int price) {
        this.destination = destination;
        this.departureDate = departureDate;
        this.flightDuration = flightDuration;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", flightDuration='" + flightDuration + '\'' +
                ", price=" + price +
                '}';
    }
}