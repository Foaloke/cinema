package com.matteo.tonnicchi.cinema.theatre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.matteo.tonnicchi.cinema.booking.Booking;
import com.matteo.tonnicchi.cinema.theatre.seat.Seat;
import com.matteo.tonnicchi.cinema.theatre.seat.SeatState;
import com.matteo.tonnicchi.utils.Pair;

public class Theatre {

    private final Integer rowCount;
    private final Integer seatPerRowCount;
    private final List<List<Seat>> rows;

    private List<Seat> createSeatRowOfLength(Integer seatCount) {
        return IntStream.range(0, seatCount).boxed().map(i -> new Seat()).collect(Collectors.toList());
    }

    public Theatre(Integer rowCount, Integer seatPerRowCount){
        this.rowCount = rowCount;
        this.seatPerRowCount = seatPerRowCount;
        this.rows = IntStream.range(0, this.rowCount).boxed()
            .map(i -> createSeatRowOfLength(this.seatPerRowCount))
            .collect(Collectors.toList());
    }

    public Theatre(Theatre theatre) {
        this.rowCount = theatre.rowCount;
        this.seatPerRowCount = theatre.seatPerRowCount;
        this.rows = IntStream.range(0, this.rowCount).boxed()
            .map(i -> createSeatRowOfLength(this.seatPerRowCount))
            .collect(Collectors.toList());
        IntStream.range(0, this.rowCount).boxed()
            .forEach(i-> IntStream.range(0, this.seatPerRowCount).boxed()
                .forEach(j ->  this.rows.get(i).get(j).setSeatState(theatre.rows.get(i).get(j).getSeatState()))
        );
    }

    public Boolean accept(Booking booking) {
        List<Pair<Integer>> requestedSeats = booking.getCoordinates();
        if(requestedSeats.size() > 5) {
            //System.out.println("Too many seats requested");
            return false;
        }
        if(someSeatsAreDuplicates(requestedSeats)) {
            //System.out.println("Some seats are duplicate");
            return false;
        }
        if(someSeatsAreOutOfTheatreRange(requestedSeats)) {
            //System.out.println("Some seats do not exist");
            return false;
        }
        if(someSeatsAreAlreadyBooked(requestedSeats)) {
            //System.out.println("Some seats are already booked");
            return false;
        }
        if(someSeatsAreNotConsecutiveOnRows(requestedSeats)) {
            //System.out.println("Some seats are not consecutive on some rows");
            return false;
        }
        if(someVacantSeatsWouldBeLeftSingled(requestedSeats)) {
            //System.out.println("Some seats would be left singled out after this booking");
            return false;
        }

        reserveSeats(requestedSeats);
        return true;
    }

    private void reserveSeats(List<Pair<Integer>> requestedSeats) {
        requestedSeats.stream().forEach(indexes
            -> this.rows.get(indexes.getLeft()).get(indexes.getRight()).setSeatState(SeatState.BOOKED));
    }

    public Boolean seatAtIndexHasState(Integer rowIndex, Integer seatIndex, SeatState seatState) {
        return this.rows.get(rowIndex).get(seatIndex).hasState(seatState);
    }

    private Boolean someSeatsAreDuplicates(List<Pair<Integer>> requestedSeats) {
        return !requestedSeats.stream().allMatch(new HashSet<>()::add);
    }

    private Boolean someSeatsAreOutOfTheatreRange(List<Pair<Integer>> requestedSeats) {
        return !requestedSeats.stream().allMatch(pair -> pair.getLeft() < this.rowCount && pair.getRight() < this.seatPerRowCount);
    }

    private Boolean someSeatsAreAlreadyBooked(List<Pair<Integer>> requestedSeats) {
        return requestedSeats.stream().anyMatch(pair -> this.rows.get(pair.getLeft()).get(pair.getRight()).hasState(SeatState.BOOKED));
    }

    private Boolean someSeatsAreNotConsecutiveOnRows(List<Pair<Integer>> requestedSeats) {
        return !requestedSeats.stream()
            .collect(Collectors.groupingBy(pair -> pair.getLeft()))  // Map<Integer, List<Pair<Integer>>
            .entrySet()
            .stream() 
            .map(entry -> areConsecutiveNumbers(entry.getValue().stream().map(pairInRow -> pairInRow.getRight()).collect(Collectors.toList())))
            .reduce(Boolean::logicalAnd)
            .orElse(Boolean.TRUE); // empty or single element is considered consecutive
    }

    private Boolean areConsecutiveNumbers(final List<Integer> integers) {
        List<Integer> integersCopy = new ArrayList<>(integers);
        Collections.sort(integersCopy);
        return IntStream.range(0, integersCopy.size() - 1).boxed()
            .map(i -> integersCopy.get(i+1).equals(integersCopy.get(i)+1))
            .reduce(Boolean::logicalAnd)
            .orElse(Boolean.TRUE); // empty or single element is considered consecutive
    }

    private Boolean someVacantSeatsWouldBeLeftSingled(List<Pair<Integer>> requestedSeats) {
        Theatre theatreCopy = new Theatre(this);
        theatreCopy.reserveSeats(requestedSeats);
        return IntStream.range(0, theatreCopy.rowCount).boxed()
            .anyMatch(i -> IntStream.range(0, theatreCopy.seatPerRowCount).boxed()
                .anyMatch(j -> {
                    Boolean seatIsVacant = theatreCopy.rows.get(i).get(j).hasState(SeatState.VACANT);
                    Boolean leftIsNotBookable = (j - 1) < 0 ? true : theatreCopy.rows.get(i).get(j-1).hasState(SeatState.BOOKED);
                    Boolean rightIsNotBookable = (j + 1) >= theatreCopy.seatPerRowCount ? true : theatreCopy.rows.get(i).get(j+1).hasState(SeatState.BOOKED);
                    return leftIsNotBookable && seatIsVacant && rightIsNotBookable;
                })
            );

    }

}