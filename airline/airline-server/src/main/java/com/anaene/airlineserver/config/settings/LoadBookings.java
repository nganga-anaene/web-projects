package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.entity.PaymentCard;
import com.anaene.airlineserver.data.repository.BookingRepository;
import com.anaene.airlineserver.data.repository.PassengerRepository;
import com.anaene.airlineserver.web.service.FlightService;
import com.anaene.airlineserver.web.service.PassengerService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class LoadBookings {

    private final PassengerService passengerService;
    private final FlightService flightService;
    private final PassengerRepository passengerRepository;

    public LoadBookings(PassengerService passengerService, FlightService flightService, PassengerRepository passengerRepository) {
        this.passengerService = passengerService;
        this.flightService = flightService;
        this.passengerRepository = passengerRepository;
    }

    public void addBookings() {
        passengerService.getAllPassengers().stream().forEach(passenger -> {
            passenger = passengerService.getPassengerById(passenger.getId());
            List<Booking> bookings = new ArrayList<>();
            for (Flight flight : getEmptyFlights()) {
                BigDecimal price = markUpDownPrice(flight.getInitialPrice());
                PaymentCard paymentCard = passenger.getPaymentCards().stream().findFirst().get();
                LocalDateTime purchaseDate = setPurchaseDate(flight.getDepartureTime());
                bookings.add(new Booking(passenger, flight, purchaseDate, price, paymentCard));
            }
            passenger.addBookings(bookings);
            passengerRepository.save(passenger);
        });
    }

    private LocalDateTime setPurchaseDate(LocalDateTime departureTime) {
        Random r = new Random();
        LocalDateTime purchaseDate = departureTime.minusDays(r.nextInt(90) + 1);
        purchaseDate = purchaseDate.withHour(r.nextInt(24));
        return purchaseDate.withMinute(r.nextInt(60));
    }

    private BigDecimal markUpDownPrice(BigDecimal initialPrice) {
        Random r = new Random();
        double mod = r.nextInt(31);
        if(mod == 0) return initialPrice;
        mod = (mod * (r.nextBoolean() ? 1 : -1)) + 100;
        mod = mod/100;
        return initialPrice.multiply(BigDecimal.valueOf(mod)).setScale(2, RoundingMode.HALF_UP);
    }

    private Set<Flight> getEmptyFlights() {
        Set<Flight> emptyFlights = new HashSet<>();
        Random r = new Random();
        int maxFlights = r.nextInt(10) + 1;
        List<Flight> flights = flightService.getEmptyFlights();
        for (int i = 0; i < maxFlights; i++) {
            try {
                int next = r.nextInt(flights.size());
                emptyFlights.add(flights.get(next));
            } catch (Exception e) {
            }
        }
        return emptyFlights;
    }
}
