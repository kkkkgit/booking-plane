package com.example.bookingplane.seatselection;

import com.example.bookingplane.flight.Flight;
import com.example.bookingplane.flight.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    public List<SeatEntity> getSeats() {
        return seatRepository.findAll();
    }

    public List<SeatEntity> getSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId);
    }

    public boolean seatsExistForFlight(Long flightId) {
        return seatRepository.existsByFlightId(flightId);
    }

    public void generateSeatsForFlight(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("flight not found with id: " + flightId));

        if (seatsExistForFlight(flightId)) {
            return;
        }

        int rows = 30;
        int seatsPerRow = 6;
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F'};

        List<SeatEntity> seats = new ArrayList<>();
        Random random = new Random();

        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < seatsPerRow; col++) {
                SeatEntity seat = new SeatEntity();
                seat.setFlight(flight);
                seat.setSeatNumber(row + String.valueOf(columns[col]));

                if (col == 0 || col == seatsPerRow - 1) {
                    seat.setSeatType(SeatEntity.SeatType.WINDOW);
                } else if (col == 2 || col == 3) {
                    seat.setSeatType((SeatEntity.SeatType.AISLE));
                } else {
                    seat.setSeatType(SeatEntity.SeatType.MIDDLE);
                }

                if (row == 1 || row == 16) {
                    seat.setNearExit(true);
                } else {
                    seat.setNearExit(false);
                }

                if (row == 1 || row == 16 || row == 17) {
                    seat.setExtraLegroom(true);
                } else {
                    seat.setExtraLegroom(false);
                }

                seat.setAvailable(random.nextDouble() > 0.3);

                seat.setFlight(flight);

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }

}
