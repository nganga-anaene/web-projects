package com.anaene.airlineserver.config.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner addPassengers(LoadPassengers passengers) {
        return args -> {
            passengers.addPassengers();
            logger.info("Passengers Loaded:");
        };
    }

    @Bean
    CommandLineRunner addClients(LoadClients clients) {
        return args -> {
            clients.addClients();
            logger.info("Clients Loaded:");
        };
    }

    @Bean
    CommandLineRunner addAirports(LoadAirports airports) {
        return args -> {
            airports.addAirports();
            logger.info("Airports Loaded:");
        };
    }

    @Bean
    CommandLineRunner addFlights(LoadFlights flights) {
        return args -> {
            flights.addFlights();
            logger.info("Flights Loaded:");
        };
    }

    @Bean
    CommandLineRunner addBookings(LoadBookings bookings) {
        return args -> {
            bookings.addBookings();
            logger.info("Bookings loaded:");
        };
    }
}
