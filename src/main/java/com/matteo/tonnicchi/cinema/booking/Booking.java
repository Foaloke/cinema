package com.matteo.tonnicchi.cinema.booking;

import java.util.List;

import com.matteo.tonnicchi.utils.Pair;

public class Booking {

    private final Integer bookingId;
    private final List<Pair<Integer>> coordinates;

    public Booking(Integer bookingId, List<Pair<Integer>> coordinates) {
        this.bookingId = bookingId;
        this.coordinates = coordinates;
    }

    public Integer getBookingId() {
        return this.bookingId;
    }

    public List<Pair<Integer>> getCoordinates() {
        return coordinates;
    }


}