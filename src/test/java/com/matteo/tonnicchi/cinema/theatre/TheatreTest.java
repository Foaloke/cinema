package com.matteo.tonnicchi.cinema.theatre;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.matteo.tonnicchi.cinema.booking.Booking;
import com.matteo.tonnicchi.cinema.theatre.seat.SeatState;
import com.matteo.tonnicchi.utils.Pair;

@SpringBootTest
class TheatreTest {

	@Test
	void theatreIsInitialisedCorrectly() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 4;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			theatre.seatAtIndexHasState(rowCount, seatPerRowCount-1, SeatState.VACANT);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			theatre.seatAtIndexHasState(rowCount-1, seatPerRowCount, SeatState.VACANT);
		});
	}

	@Test
	void acceptsValidBooking() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 4;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		List<Pair<Integer>> bookingCoordinates = new ArrayList<>();
		bookingCoordinates.add(new Pair<>(0,2));
		bookingCoordinates.add(new Pair<>(0,3));
		bookingCoordinates.add(new Pair<>(1,0));
		bookingCoordinates.add(new Pair<>(1,1));
		Booking booking = new Booking(0, bookingCoordinates);

		assertTrue(theatre.accept(booking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.BOOKED));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));

	}

	@Test
	void rejectsBookingIfSeatDoesNotExist() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 4;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		List<Pair<Integer>> bookingCoordinates = new ArrayList<>();
		bookingCoordinates.add(new Pair<>(0,4));
		Booking booking = new Booking(0, bookingCoordinates);

		assertFalse(theatre.accept(booking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));
	}

	@Test
	void rejectsBookingIfSeatAreNotConsecutiveOnRows() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 10;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		List<Pair<Integer>> bookingCoordinates = new ArrayList<>();
		bookingCoordinates.add(new Pair<>(0,1));
		bookingCoordinates.add(new Pair<>(0,2));
		bookingCoordinates.add(new Pair<>(0,3));
		bookingCoordinates.add(new Pair<>(1,2));
		bookingCoordinates.add(new Pair<>(1,7));
		Booking booking = new Booking(0, bookingCoordinates);

		assertFalse(theatre.accept(booking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 4, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 5, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 6, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 7, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 8, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 9, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 4, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 5, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 6, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 7, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 8, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 9, SeatState.VACANT));
	}

	@Test
	void rejectsBookingIfAnySeatIsNotVacant() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 4;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		List<Pair<Integer>> bookingCoordinates = new ArrayList<>();
		bookingCoordinates.add(new Pair<>(0,0));
		bookingCoordinates.add(new Pair<>(0,1));
		Booking booking = new Booking(0, bookingCoordinates);

		assertTrue(theatre.accept(booking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));

		List<Pair<Integer>> otherBookingCoordinates = new ArrayList<>();
		otherBookingCoordinates.add(new Pair<>(0,1));
		otherBookingCoordinates.add(new Pair<>(0,3));
		Booking otherBooking = new Booking(1, otherBookingCoordinates);

		assertFalse(theatre.accept(otherBooking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));
	}

	@Test
	void rejectsBookingIfMultipleRequestForSameSeat() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 4;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		List<Pair<Integer>> bookingCoordinates = new ArrayList<>();
		bookingCoordinates.add(new Pair<>(0,1));
		bookingCoordinates.add(new Pair<>(0,2));
		bookingCoordinates.add(new Pair<>(0,2));
		Booking booking = new Booking(0, bookingCoordinates);

		assertFalse(theatre.accept(booking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));
	}

	@Test
	void rejectsBookingIfThatWouldLeadToSingleVacantSeat() {
        Integer rowCount = 2;
        Integer seatPerRowCount = 5;
        Theatre theatre = new Theatre(rowCount, seatPerRowCount);

		List<Pair<Integer>> bookingCoordinates = new ArrayList<>();
		bookingCoordinates.add(new Pair<>(0,2));
		bookingCoordinates.add(new Pair<>(1,0));
		Booking booking = new Booking(0, bookingCoordinates);

		assertTrue(theatre.accept(booking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 4, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 4, SeatState.VACANT));

		List<Pair<Integer>> otherBookingCoordinates = new ArrayList<>();
		otherBookingCoordinates.add(new Pair<>(1,2));
		otherBookingCoordinates.add(new Pair<>(1,3));
		otherBookingCoordinates.add(new Pair<>(1,4));
		Booking otherBooking = new Booking(1, otherBookingCoordinates);

		assertFalse(theatre.accept(otherBooking));

		assertTrue(theatre.seatAtIndexHasState(0, 0, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 2, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(0, 3, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(0, 4, SeatState.VACANT));

		assertTrue(theatre.seatAtIndexHasState(1, 0, SeatState.BOOKED));
		assertTrue(theatre.seatAtIndexHasState(1, 1, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 2, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 3, SeatState.VACANT));
		assertTrue(theatre.seatAtIndexHasState(1, 4, SeatState.VACANT));
	}

}