package com.example.bookingplane.seatselection;

import com.example.bookingplane.flight.Flight;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
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

    private String seatNumber; // e.g., "A1", "B3", etc.
    private int row;
    @Column(name = "\"column\"")
    private String column; // e.g., "A", "B", "C", etc.

    private boolean isWindowSeat;
    private boolean isAisleSeat;
    private boolean hasExtraLegroom;
    private boolean isExitRowSeat;
    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    @JsonBackReference
    private Flight flight;

    private SeatClass seatClass;

    public enum SeatClass {
        FIRST_CLASS,
        BUSINESS_CLASS,
        ECONOMY_CLASS
    }

    public Seat() {
    }

    public Seat(String seatNumber, int row, String column,
                boolean isWindowSeat, boolean isAisleSeat,
                boolean hasExtraLegroom, boolean isExitRowSeat,
                boolean isReserved, Flight flight, SeatClass seatClass) {
        this.seatNumber = seatNumber;
        this.row = row;
        this.column = column;
        this.isWindowSeat = isWindowSeat;
        this.isAisleSeat = isAisleSeat;
        this.hasExtraLegroom = hasExtraLegroom;
        this.isExitRowSeat = isExitRowSeat;
        this.isReserved = isReserved;
        this.flight = flight;
        this.seatClass = seatClass;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isWindowSeat() {
        return isWindowSeat;
    }

    public void setWindowSeat(boolean windowSeat) {
        isWindowSeat = windowSeat;
    }

    public boolean isAisleSeat() {
        return isAisleSeat;
    }

    public void setAisleSeat(boolean aisleSeat) {
        isAisleSeat = aisleSeat;
    }

    public boolean isHasExtraLegroom() {
        return hasExtraLegroom;
    }

    public void setHasExtraLegroom(boolean hasExtraLegroom) {
        this.hasExtraLegroom = hasExtraLegroom;
    }

    public boolean isExitRowSeat() {
        return isExitRowSeat;
    }

    public void setExitRowSeat(boolean exitRowSeat) {
        isExitRowSeat = exitRowSeat;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatNumber='" + seatNumber + '\'' +
                ", row=" + row +
                ", column='" + column + '\'' +
                ", isWindowSeat=" + isWindowSeat +
                ", isAisleSeat=" + isAisleSeat +
                ", hasExtraLegroom=" + hasExtraLegroom +
                ", isExitRowSeat=" + isExitRowSeat +
                ", isReserved=" + isReserved +
                ", flight=" + (flight != null ? flight.getId() : null) +
                ", seatClass=" + seatClass +
                '}';
    }
}