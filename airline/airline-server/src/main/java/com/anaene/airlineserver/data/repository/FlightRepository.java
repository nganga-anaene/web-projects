package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureTimeAfter(LocalDateTime departureTime);

    Page<Flight> findAllByDepartingAirportAndArrivalAirportAndDepartureTimeBetween(Airport departingAirport, Airport arrivalAirport, Pageable pageable, LocalDateTime date1, LocalDateTime date2);
}
