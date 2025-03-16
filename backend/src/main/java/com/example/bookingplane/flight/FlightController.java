package com.example.bookingplane.flight;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "flight")
public class FlightController {

    @GetMapping
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
