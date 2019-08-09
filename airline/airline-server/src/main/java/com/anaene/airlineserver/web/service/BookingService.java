package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.repository.BookingRepository;
import com.anaene.airlineserver.web.resource.BookingResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingResourceAssembler bookingResourceAssembler;

    public BookingService(BookingRepository bookingRepository, BookingResourceAssembler bookingResourceAssembler) {
        this.bookingRepository = bookingRepository;
        this.bookingResourceAssembler = bookingResourceAssembler;
    }

    public BookingResourceAssembler getBookingResourceAssembler() {
        return bookingResourceAssembler;
    }

    @Transactional
    public Booking getBookingById(long id) {
        return bookingRepository.findFullBookingById(id).orElseThrow();
    }

    public Resource<Booking> getBookingResource(Booking booking) {
        return bookingResourceAssembler.toResource(booking);
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
}
