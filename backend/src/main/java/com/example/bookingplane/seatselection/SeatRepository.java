package com.example.bookingplane.seatselection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightId(Long flightId);
    List<Seat> findByFlightIdAndIsReserved(Long flightId, boolean isReserved);

    @Query("SELECT s FROM Seat s WHERE s.flight.id = ?1 AND s.isReserved = false " +
            "AND ((?2 = false) OR (s.isWindowSeat = true)) " +
            "AND ((?3 = false) OR (s.isAisleSeat = true)) " +
            "AND ((?4 = false) OR (s.hasExtraLegroom = true)) " +
            "AND ((?5 = false) OR (s.isExitRowSeat = true))")

    List<Seat> findSeatsByPreferences(Long flightId, boolean wantsWindowSeat, boolean wantsAisleSeat, boolean wantsExtraLegroom, boolean wantsExitRow);
}
