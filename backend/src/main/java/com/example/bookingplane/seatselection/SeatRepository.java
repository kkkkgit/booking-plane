package com.example.bookingplane.seatselection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByFlightId(Long flightId);
    boolean existsByFlightId(Long flightId);
    List<SeatEntity> findByFlightIdAndAvailableTrue(Long flightId);
}
