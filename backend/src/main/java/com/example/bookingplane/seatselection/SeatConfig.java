package com.example.bookingplane.seatselection;

import com.example.bookingplane.flight.Flight;
import com.example.bookingplane.flight.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.*;

@Configuration
public class SeatConfig {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final SeatService seatService;

    @Autowired
    public SeatConfig(SeatRepository seatRepository,  FlightRepository flightRepository, SeatService seatService) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.seatService = seatService;
    }

    @Bean
    @DependsOn("commandLineRunner")
    CommandLineRunner seatInitializer(FlightRepository flightRepository) {
        return args -> {
            try {
                // Get all flights from repository
                List<Flight> flights = flightRepository.findAll();
                System.out.println("Found " + flights.size() + " flights for seat initialization");

                for (Flight flight : flights) {
                        Long flightId = flight.getId();
                        System.out.println("Initializing flight " + flightId);

                        List<Seat> existingSeats = seatRepository.findByFlightId(flightId);

                        if (existingSeats.isEmpty()) {
                        List<Seat> generatedSeats = seatService.generateSeatsForFlight(flightId, 15, 6);
                        }
                }
            } catch (Exception e) {
                System.err.println("Error in seat initialization: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
