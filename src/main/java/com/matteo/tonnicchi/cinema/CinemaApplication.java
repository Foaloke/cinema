package com.matteo.tonnicchi.cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.matteo.tonnicchi.cinema.booking.Booking;
import com.matteo.tonnicchi.cinema.booking.BookingHelper;
import com.matteo.tonnicchi.cinema.theatre.Theatre;
import com.matteo.tonnicchi.utils.FileParser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class CinemaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CinemaApplication.class, args);

        Resource file = new ClassPathResource("booking_requests");
		Theatre theatre = new Theatre(100, 50);
		
		List<Booking> rejected = new ArrayList<>();
		FileParser.parse(
			file.getInputStream(),
			line -> {
				Booking booking = BookingHelper.buildBookingFromLine(line);
				Boolean accepted = theatre.accept(booking);
				if(!accepted) {
					rejected.add(booking);
				};
			}
		);

		List<String> rejectedIds = rejected
			.stream()
			.map(rejectedBooking -> String.valueOf(rejectedBooking.getBookingId()))
			.collect(Collectors.toList());

		String rejectedBookingIds = String.join("," , rejectedIds);
		Integer rejectedSize = rejected.size();
		System.out.println(
			rejectedSize + " booking" + (rejectedSize > 1 ? "s" : "")
			+ " were rejected: [" + rejectedBookingIds + "]" );

	}

}
