package com.matteo.tonnicchi.cinema.booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import com.matteo.tonnicchi.utils.Pair;

@SpringBootTest
public class BookingHelperTest {

    private Boolean onePairInBooking(Booking booking, Predicate<Pair<Integer>> assertion){
        return booking.getCoordinates().stream().anyMatch(assertion);
    }

    private Predicate<Pair<Integer>> hasCoords(Integer left, Integer right) {
        return pair -> pair.getLeft().equals(left) && pair.getRight().equals(right);
    }

	@Test
	void shouldParseBookingOfOneSeatCorrectly() {
        String bookingLine = "(5,24:31,24:31),";
        Booking booking = BookingHelper.buildBookingFromLine(bookingLine);
        assertEquals(booking.getBookingId(), 5);

        assertEquals(booking.getCoordinates().size(), 1);

        assertTrue(onePairInBooking(booking, hasCoords(24, 31)));
    }

	@Test
	void shouldParseBookingOfMultipleSeatsOnOneRowCorrectly() {
        String bookingLine = "(5,24:31,24:37),";
        Booking booking = BookingHelper.buildBookingFromLine(bookingLine);
        assertEquals(booking.getBookingId(), 5);

        assertEquals(booking.getCoordinates().size(), 7);

        assertTrue(onePairInBooking(booking, hasCoords(24, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(24, 32)));
        assertTrue(onePairInBooking(booking, hasCoords(24, 33)));
        assertTrue(onePairInBooking(booking, hasCoords(24, 34)));
        assertTrue(onePairInBooking(booking, hasCoords(24, 35)));
        assertTrue(onePairInBooking(booking, hasCoords(24, 36)));
        assertTrue(onePairInBooking(booking, hasCoords(24, 37)));
    }

	@Test
	void shouldParseBookingOfSingleSeatsOnMultipleRowsCorrectly() {
        String bookingLine = "(5,24:31,29:31),";
        Booking booking = BookingHelper.buildBookingFromLine(bookingLine);
        assertEquals(booking.getBookingId(), 5);

        assertEquals(booking.getCoordinates().size(), 6);

        assertTrue(onePairInBooking(booking, hasCoords(24, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(25, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(26, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(27, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(28, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(29, 31)));
    }

	@Test
	void shouldParseBookingOfMultipleSeatsOnMultipleRowsCorrectly() {
        String bookingLine = "(5,24:31,29:33),";
        Booking booking = BookingHelper.buildBookingFromLine(bookingLine);
        assertEquals(booking.getBookingId(), 5);

        assertEquals(booking.getCoordinates().size(), 18);

        assertTrue(onePairInBooking(booking, hasCoords(24, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(25, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(26, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(27, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(28, 31)));
        assertTrue(onePairInBooking(booking, hasCoords(29, 31)));

        assertTrue(onePairInBooking(booking, hasCoords(24, 32)));
        assertTrue(onePairInBooking(booking, hasCoords(25, 32)));
        assertTrue(onePairInBooking(booking, hasCoords(26, 32)));
        assertTrue(onePairInBooking(booking, hasCoords(27, 32)));
        assertTrue(onePairInBooking(booking, hasCoords(28, 32)));
        assertTrue(onePairInBooking(booking, hasCoords(29, 32)));

        assertTrue(onePairInBooking(booking, hasCoords(24, 33)));
        assertTrue(onePairInBooking(booking, hasCoords(25, 33)));
        assertTrue(onePairInBooking(booking, hasCoords(26, 33)));
        assertTrue(onePairInBooking(booking, hasCoords(27, 33)));
        assertTrue(onePairInBooking(booking, hasCoords(28, 33)));
        assertTrue(onePairInBooking(booking, hasCoords(29, 33)));
    }
}
