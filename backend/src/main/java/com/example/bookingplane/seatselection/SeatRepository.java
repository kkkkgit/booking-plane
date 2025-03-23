package com.example.bookingplane.seatselection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    // Finding all seats for a specific flight
    List<SeatEntity> findByFlightId(Long flightId);
    // Find all available seats for the specific flight
    List<SeatEntity> findByAvailable(boolean available, Long flightId);
    // Find available window seats
    List<SeatEntity> findByWindowSeats(boolean available, SeatEntity.SeatType seatType, Long flightId);
    // Find extra legroom seats that are available
    List<SeatEntity> findByExtraLegroom(boolean available, boolean extraLegroom, Long flightId);
    // Find seats near exits that are available
    List<SeatEntity> findByNearExit(boolean available, boolean nearExit, Long flightId);
}
