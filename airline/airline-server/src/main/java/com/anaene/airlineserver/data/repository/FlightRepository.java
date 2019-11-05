package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Page<Flight> findByDepartingAirportAndArrivalAirportAndDepartureTimeAfter(Airport departingAirport, Airport arrivalAirport, LocalDateTime departureTime, Pageable pageable);

    List<Flight> findByDepartingAirportAndArrivalAirportAndDepartureTimeAfter(Airport departingAirport, Airport arrivalAirport, LocalDateTime departureTime);

    Page<Flight> findByDepartingAirport(Airport departingAirport, Pageable pageable);

    Page<Flight> findByArrivalAirport(Airport arrivalAirport, Pageable pageable);

    List<Flight> findByDepartureTimeAfterAndFeatured(LocalDateTime departureTime, boolean featured);
}
