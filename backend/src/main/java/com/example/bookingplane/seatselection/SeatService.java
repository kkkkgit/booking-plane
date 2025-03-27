package com.example.bookingplane.seatselection;

import com.example.bookingplane.flight.Flight;
import com.example.bookingplane.flight.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


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

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public List<Seat> getSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightId(flightId);
    }

    public List<Seat> getAvailableSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightIdAndIsReserved(flightId, false);
    }

    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> generateSeatsForFlight(Long flightId, int rows, int columns) {
        Optional<Flight> flightOptional = flightRepository.findById(flightId);

        if (flightOptional.isEmpty()) {
            throw new IllegalArgumentException("Flight not found with id: " + flightId);
        }

        Flight flight = flightOptional.get();

        String[] columnLetters = {"A", "B", "C", "D", "E", "F", "G", "H"};

        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < columns && col < columnLetters.length; col++) {
                String column = columnLetters[col];
                String seatNumber = row + column;

                boolean isWindowSeat = col == 0 || col == columns - 1;
                boolean isAisleSeat = (columns <= 3) ? false : (col == columns / 2 - 1 || col == columns / 2);
                boolean hasExtraLegroom = row == 1 || row == Math.round(rows / 3.0);
                boolean isExitRowSeat = row == Math.round(rows / 2.0);
                boolean isReserved = random.nextBoolean();

                Seat.SeatClass seatClass;
                if (row <= rows / 6) {
                    seatClass = Seat.SeatClass.FIRST_CLASS;
                } else if (row <= rows / 3) {
                    seatClass = Seat.SeatClass.BUSINESS_CLASS;
                } else {
                    seatClass = Seat.SeatClass.ECONOMY_CLASS;
                }

                Seat seat = new Seat(
                        seatNumber, row, column, isWindowSeat, isAisleSeat, hasExtraLegroom, isExitRowSeat, isReserved, flight, seatClass
                );

                flight.addSeat(seat);
                seatRepository.save(seat);
            }
        }
        return flight.getSeats();
    }
    public List<Seat> recommendSeats(Long flightId, SeatPreferences preferences, int count) {
        List<Seat> recommendedSeats = seatRepository.findSeatsByPreferences(
                flightId,
                preferences.isWantsWindowSeat(),
                preferences.isWantsAisleSeat(),
                preferences.isWantsExtraLegroom(),
                preferences.isWantsExitRowSeat()
        );

        if (recommendedSeats.isEmpty()) {
            recommendedSeats = seatRepository.findByFlightIdAndIsReserved(flightId, false);
            System.out.println("No seats matched preferences, falling back to " + recommendedSeats.size() + " available seats");
        }

        return recommendedSeats.stream().limit(count).toList();
    }

    public boolean reserveSeat(Long seatId) {
        Optional<Seat> seatOptional = seatRepository.findById(seatId);
        if (seatOptional.isPresent()) {
            Seat seat = seatOptional.get();
            if (!seat.isReserved()) {
                seat.setReserved(true);
                seatRepository.save(seat);
                return true;
            }
        }
        return false;
    }
}