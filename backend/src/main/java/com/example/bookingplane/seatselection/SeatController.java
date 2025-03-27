package com.example.bookingplane.seatselection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/flight/{flightId}")
    public List<Seat> getSeatsByFlight(@PathVariable Long flightId) {
        return seatService.getSeatsByFlight(flightId);
    }

    @GetMapping("/flight/{flightId}/available")
    public List<Seat> getAvailableSeatsByFlight(@PathVariable Long flightId) {
        return seatService.getAvailableSeatsByFlight(flightId);
    }

    @PostMapping("/flight/{flightId}/generate")
    public List<Seat> generateSeats(
            @PathVariable Long flightId,
            @RequestParam int rows,
            @RequestParam int columns) {
        return seatService.generateSeatsForFlight(flightId, rows, columns);
    }

    @PostMapping("/flight/{flightId}/recommend")
    public List<Seat> recommendSeats(
            @PathVariable Long flightId,
            @RequestBody SeatPreferences preferences,
            @RequestParam int count) {
        return seatService.recommendSeats(flightId, preferences, count);
    }

    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<Map<String, Boolean>> reserveSeat(@PathVariable Long seatId) {
        boolean success = seatService.reserveSeat(seatId);
        return ResponseEntity.ok(Map.of("success", success));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        Optional<Seat> seat = seatService.getSeatById(id);
        return seat.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
