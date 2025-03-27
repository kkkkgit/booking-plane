package com.example.bookingplane.seatselection;

import com.example.bookingplane.flight.Flight;
import jakarta.persistence.*;

@Entity
@Table
public class SeatEntity {
    @Id
    @SequenceGenerator(
            name = "seat_sequence",
            sequenceName = "seat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seat_sequence"
    )

    private Long id;
    private String seatNumber;
    private String seatClass;
    private boolean isWindowSeat;
    private boolean hasExtraLegroom;
    private boolean isNearExit;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;


    public SeatEntity() {
    }

    public SeatEntity(String seatNumber, String seatClass, boolean isWindowSeat, boolean hasExtraLegroom, boolean isNearExit, boolean isAvailable, Flight flight) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isWindowSeat = isWindowSeat;
        this.hasExtraLegroom = hasExtraLegroom;
        this.isNearExit = isNearExit;
        this.isAvailable = isAvailable;
        this.flight = flight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public boolean isWindowSeat() {
        return isWindowSeat;
    }

    public void setWindowSeat(boolean windowSeat) {
        isWindowSeat = windowSeat;
    }

    public boolean isHasExtraLegroom() {
        return hasExtraLegroom;
    }

    public void setHasExtraLegroom(boolean hasExtraLegroom) {
        this.hasExtraLegroom = hasExtraLegroom;
    }

    public boolean isNearExit() {
        return isNearExit;
    }

    public void setNearExit(boolean nearExit) {
        isNearExit = nearExit;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "SeatEntity{" +
                "id=" + id +
                ", seatNumber='" + seatNumber + '\'' +
                ", seatClass='" + seatClass + '\'' +
                ", isWindowSeat=" + isWindowSeat +
                ", hasExtraLegroom=" + hasExtraLegroom +
                ", isNearExit=" + isNearExit +
                ", isAvailable=" + isAvailable +
                ", flight=" + flight.getId() +
                '}';
    }
}
