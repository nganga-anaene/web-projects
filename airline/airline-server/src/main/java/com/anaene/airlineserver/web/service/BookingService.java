package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.repository.BookingRepository;
import com.anaene.airlineserver.web.resource.BookingResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingResourceAssembler bookingResourceAssembler;

    public BookingService(BookingRepository bookingRepository, BookingResourceAssembler bookingResourceAssembler) {
        this.bookingRepository = bookingRepository;
        this.bookingResourceAssembler = bookingResourceAssembler;
    }

    @Transactional
    public Booking getBookingById(long id) {
        return bookingRepository.findFullBookingById(id).orElseThrow();
    }

    public Resource<Booking> getBookingResource(Booking booking) {
        return bookingResourceAssembler.toResource(booking);
    }

    @Transactional
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

}
