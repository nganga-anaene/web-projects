package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.data.repository.FlightRepository;
import com.anaene.airlineserver.web.controller.util.FlightDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightService(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Transactional
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Transactional
    public List<Flight> getEmptyFlights() {
        return flightRepository.findAll().stream().filter(f -> f.getBookings().size() < f.getMaxBookings()).collect(Collectors.toList());
    }

    public long countFlights() {
        return flightRepository.count();
    }

    public Page<Flight> getFlightByDay(@NotNull FlightDetail flightDetail, int page, int size) {
        Airport arrivalAirport = airportRepository.findById(flightDetail.getArrivalAirportId()).orElseThrow();
        Airport departureAirport = airportRepository.findById(flightDetail.getDepartureAirportId()).orElseThrow();
        LocalDateTime date1 = flightDetail.getFlightDate().atTime(0, 0);
        LocalDateTime date2 = date1.plusDays(1);
        return flightRepository.findAllByDepartingAirportAndArrivalAirportAndDepartureTimeBetween(departureAirport, arrivalAirport, PageRequest.of(page, size), date1, date2);
    }
}
