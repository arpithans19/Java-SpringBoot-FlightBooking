package com.booking.repositorytests;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;

@SpringBootTest
public class BookingRepositoryTests {

	@Mock
	private BookingRepository bookingRepository;
	private Booking booking;
}
