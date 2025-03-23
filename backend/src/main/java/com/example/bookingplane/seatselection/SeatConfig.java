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
            // Get all flights from repository
            List<Flight> flights = flightRepository.findAll();

            if (flights.isEmpty()) {
                System.out.println("No flights found to initialize seats for.");
                return;
            }

            System.out.println("Initializing flights for " + flights.size() + " flights.");

            // for each flight, generate seats
            for (Flight flight : flights) {
                if (seatRepository.findByFlightId(flight.getId()).isEmpty()) {
                    seatService.generateTakenSeats(flight);
                    System.out.println("Generated seats for flight: " + flight.getId());
                } else {
                    System.out.println("Seats for flight: " + flight.getId() +  " already exist.");
                }
            }
        };
    }
}
