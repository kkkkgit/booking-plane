package com.example.bookingplane.seatselection;

import com.example.bookingplane.flight.Flight;
import com.example.bookingplane.flight.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final Random random = new Random();

    @Autowired
    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    public List<SeatEntity> getAllSeatsForFlight(Long flightId) {
        return seatRepository.findByFlightId(flightId);
    }

    public List<SeatEntity> getAllAvailableSeats(boolean available, Long flightId) {
        return seatRepository.findByAvailable(true, flightId);
    }

    public List<SeatEntity> generateTakenSeats(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("flight with id " + flightId + " not found"));

        return generateTakenSeats(flight);
    }

    public List<SeatEntity> generateTakenSeats(Flight flight) {
        List<SeatEntity> seats = new ArrayList<>();

        int rows = 30;
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F'};

        for (int row = 1; row <= rows; row++) {
            for (char column :  columns) {
                SeatEntity.SeatType seatType;

                if (column == 'A' || column == 'F') {
                    seatType = SeatEntity.SeatType.WINDOW;
                } else if (column == 'C' || column == 'E') {
                    seatType = SeatEntity.SeatType.AISLE;
                } else {
                    seatType = SeatEntity.SeatType.MIDDLE;
                }

                boolean extraLegroom = (row == 1 || row == 30);
                boolean nearExit = (row == 1 || row == 30);

                boolean available = random.nextDouble() < 0.7;

                SeatEntity seat = new SeatEntity(
                        row + String.valueOf(column),
                        seatType,
                        row,
                        column,
                        extraLegroom,
                        nearExit,
                        available,
                        flight
                );

                seats.add(seat);
            }
        }
        return seatRepository.saveAll(seats);
    }
}