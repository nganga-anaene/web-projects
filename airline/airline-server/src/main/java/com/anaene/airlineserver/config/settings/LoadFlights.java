package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.data.repository.FlightRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        LocalDateTime time = LocalDate.now().atStartOfDay().minusMonths(1);
        LocalDateTime endOfBookings = time.plusMonths(3);
        while (time.isBefore(endOfBookings)) {
            for (int i = 0; i < numberOfFlightsPerDay; i++) {
                LocalDateTime departingTime = setTime(time);
                LocalDateTime arrivalTime = setTime(departingTime);
                BigDecimal price = setPrice();
                if (arrivalTime.isBefore(departingTime) || arrivalTime.equals(departingTime)) {
                    arrivalTime = arrivalTime.plusDays(1);
                }
                flightRepository.save(new Flight(departureAirport, arrivalAirport, departingTime, arrivalTime, price, setFeatured()));
            }
            time = time.plusDays(1);
        }
    }

    private boolean setFeatured() {
        Random r = new Random();
        int next = r.nextInt(5);
        return next % 3 == 0;
    }

    private LocalDateTime setTime(LocalDateTime time) {
        Random r = new Random();
        int durationHours = r.nextInt(10) + 3;
        int durationMinutes = r.nextInt(58) + 1;
        return time.plusMinutes(durationMinutes).plusHours(durationHours);
    }

    private BigDecimal setPrice() {
        Random r = new Random();
        return BigDecimal.valueOf(r.nextInt(400) + 400);
    }

}
