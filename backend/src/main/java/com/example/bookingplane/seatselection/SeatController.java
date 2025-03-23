package com.example.bookingplane.seatselection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/flight/{flightId}")
    public List<SeatEntity> getAllSeatsForFlight(@PathVariable Long flightId) {
        return seatService.getAllSeatsForFlight(flightId);
    }

    @GetMapping("/flight/{flightId}/available")
    public List<SeatEntity> getAvailableSeatsForFlight(@PathVariable Long flightId) {
        return seatService.getAllAvailableSeats(true, flightId);
    }

    @PostMapping("/flight/{flightId}/generate")
    public List<SeatEntity> generateSeatsForFlight(@PathVariable Long flightId) {
        return seatService.generateTakenSeats(flightId);
    }
}
