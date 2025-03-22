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

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private int seatRow;
    private Character seatCol;
    private boolean extraLegroom;
    private boolean nearExit;
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public enum SeatType {
        WINDOW,
        MIDDLE,
        AISLE
    }

    public SeatEntity() {
    }

    public SeatEntity(Long id, String seatNumber, SeatType seatType, int seatRow,
                      Character seatCol, boolean extraLegroom, boolean nearExit,
                      boolean available, Flight flight) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatRow = seatRow;
        this.seatCol = seatCol;
        this.extraLegroom = extraLegroom;
        this.nearExit = nearExit;
        this.available = available;
        this.flight = flight;
    }

    public SeatEntity(String seatNumber, SeatType seatType, int seatRow,
                      Character seatCol, boolean extraLegroom, boolean nearExit,
                      boolean available, Flight flight) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatRow = seatRow;
        this.seatCol = seatCol;
        this.extraLegroom = extraLegroom;
        this.nearExit = nearExit;
        this.available = available;
        this.flight = flight;
    }

    // Getters and setters

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

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public Character getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(Character seatCol) {
        this.seatCol = seatCol;
    }

    public boolean isExtraLegroom() {
        return extraLegroom;
    }

    public void setExtraLegroom(boolean extraLegroom) {
        this.extraLegroom = extraLegroom;
    }

    public boolean isNearExit() {
        return nearExit;
    }

    public void setNearExit(boolean nearExit) {
        this.nearExit = nearExit;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
                ", seatType=" + seatType +
                ", seatRow=" + seatRow +
                ", seatCol=" + seatCol +
                ", extraLegroom=" + extraLegroom +
                ", nearExit=" + nearExit +
                ", available=" + available +
                ", flight=" + (flight != null ? flight.getId() : null) +
                '}';
    }
}
