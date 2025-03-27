package com.example.bookingplane.seatselection;

public class SeatPreferences {
    private boolean wantsWindowSeat;
    private boolean wantsAisleSeat;
    private boolean wantsExtraLegroom;
    private boolean wantsExitRowSeat;
    private Seat.SeatClass preferredClass;

    public SeatPreferences() {

    }


    public SeatPreferences(boolean wantsWindowSeat, boolean wantsAisleSeat, boolean wantsExtraLegroom, boolean wantsExitRowSeat, Seat.SeatClass preferredClass) {
        this.wantsWindowSeat = wantsWindowSeat;
        this.wantsAisleSeat = wantsAisleSeat;
        this.wantsExtraLegroom = wantsExtraLegroom;
        this.wantsExitRowSeat = wantsExitRowSeat;
        this.preferredClass = preferredClass;
    }


    public boolean isWantsWindowSeat() {
        return wantsWindowSeat;
    }

    public void setWantsWindowSeat(boolean wantsWindowSeat) {
        this.wantsWindowSeat = wantsWindowSeat;
    }

    public boolean isWantsAisleSeat() {
        return wantsAisleSeat;
    }

    public void setWantsAisleSeat(boolean wantsAisleSeat) {
        this.wantsAisleSeat = wantsAisleSeat;
    }

    public boolean isWantsExtraLegroom() {
        return wantsExtraLegroom;
    }

    public void setWantsExtraLegroom(boolean wantsExtraLegroom) {
        this.wantsExtraLegroom = wantsExtraLegroom;
    }

    public boolean isWantsExitRowSeat() {
        return wantsExitRowSeat;
    }

    public void setWantsExitRowSeat(boolean wantsExitRowSeat) {
        this.wantsExitRowSeat = wantsExitRowSeat;
    }

    public Seat.SeatClass getPreferredClass() {
        return preferredClass;
    }

    public void setPreferredClass(Seat.SeatClass preferredClass) {
        this.preferredClass = preferredClass;
    }
}

