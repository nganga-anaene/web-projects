package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.entity.Passenger;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(value = "booking.full")
    Optional<Booking> findFullBookingById(Long id);

    @EntityGraph(value = "booking.full")
    Optional<Booking> findByPassengerAndFlight(Passenger passenger, Flight flight);
}
