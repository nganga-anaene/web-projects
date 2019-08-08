package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.data.repository.FlightRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class LoadFlights {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    LoadFlights(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    public void addFlights() {
        airportRepository.findAll().forEach(airport -> {
            List<Airport> airports = airportRepository.findAll().stream().filter(a -> !a.equals(airport)).collect(Collectors.toList());
            setFlights(airport, airports);
        });
        airportRepository.findAll().forEach(airport -> System.out.println("airport id: " + airport.getId()));
    }

    private void setFlights(Airport departureAirport, List<Airport> airports) {
        airports.forEach(arrivalAirport -> {
            Random r = new Random();
            int numberOfFlightsPerDay = r.nextInt(2) + 1;
            setFlight(departureAirport, arrivalAirport, numberOfFlightsPerDay);
        });
    }

    private void setFlight(Airport departureAirport, Airport arrivalAirport, int numberOfFlightsPerDay) {
        Random r = new Random();
        int durationHours = r.nextInt(10) + 4;
        int durationMinutes = r.nextInt(59) + 1;
        LocalDateTime time = LocalDateTime.now().withSecond(0).withMinute(0).withHour(0).minusMonths(1);
        LocalDateTime endOfBookings = time.plusMonths(3);
        while (time.isBefore(endOfBookings)) {
            for (int i = 0; i < numberOfFlightsPerDay; i++) {
                LocalDateTime departingTime = time.plusHours(r.nextInt(24));
                LocalDateTime arrivalTime = departingTime.plusHours(durationHours).plusMinutes(durationMinutes);
                flightRepository.save(new Flight(departureAirport, arrivalAirport, departingTime, arrivalTime));
            }
            time = time.plusDays(1);
        }
    }

}
