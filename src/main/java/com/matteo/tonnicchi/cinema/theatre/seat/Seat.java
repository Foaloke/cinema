package com.matteo.tonnicchi.cinema.theatre.seat;

public class Seat {

    private SeatState seatState;

    public Seat(){
        this.seatState = SeatState.VACANT;
    }

    public Boolean hasState(SeatState state) {
        return this.seatState.equals(state);
    }

    public void setSeatState(SeatState state) {
        this.seatState = state;
    }

    public SeatState getSeatState() {
        return this.seatState;
    }

}