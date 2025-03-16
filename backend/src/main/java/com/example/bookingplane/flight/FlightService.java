package com.example.bookingplane.flight;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class FlightService {
    public List<Flight> getFlights() {
        return List.of(
                new Flight(
                        1L,
                        "Viin",
                        LocalDate.of(2025, Month.APRIL, 5),
                        "1h 30min",
                        150
                )
        );
    }
}
