package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.entity.Passenger;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(value = "booking.full")
    Optional<Booking> findFullBookingById(Long id);

    @EntityGraph(value = "booking.full")
    Optional<Booking> findByPassengerAndFlightsIn(Passenger passenger, Flight flight);

    @EntityGraph(value = "booking.full")
    List<Booking> findByFlightsContaining(Flight flight);

    @EntityGraph(value = "booking.full")
    void deleteByIdAndPassengerAndFlights(long bookingId, Passenger passenger, Set<Flight> flights);
}
