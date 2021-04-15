package com.matteo.tonnicchi.cinema.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import com.matteo.tonnicchi.utils.Pair;

public final class BookingHelper {
    
    public static Booking buildBookingFromLine(String bookingLine) {

      List<Pair<Integer>> coordinates = new ArrayList<>();

      // E.g. (5,24:31,24:31),
      String pattern = "\\((.*?),(.*?),(.*?)\\)";
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(bookingLine);
      
      if (m.find()) {
         Integer bookingId = Integer.parseInt(m.group(1));
    
         Pair<Integer> startCoordinates = toPair(m.group(2));
         Pair<Integer> endCoordinates = toPair(m.group(3));

         Integer startRow = startCoordinates.getLeft();
         Integer endRow = endCoordinates.getLeft();

         Integer startSeat = startCoordinates.getRight();
         Integer endSeat = endCoordinates.getRight();

         IntStream.range(startRow, endRow+1).boxed().forEach(rowIndex -> {
             IntStream.range(startSeat, endSeat+1).boxed().forEach(seatIndex -> {
                coordinates.add(new Pair<Integer>(rowIndex, seatIndex));
             });
         });

         return new Booking(bookingId, coordinates);

      } else {
          throw new IllegalArgumentException("Could not read booking line with format " + bookingLine);
      }
    }

    private static Pair<Integer> toPair(String group) {
        String[] coords = group.split(":");
        return new Pair<Integer>(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
    }

}
