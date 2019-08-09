package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.*;
import com.anaene.airlineserver.data.repository.FlightRepository;
import com.anaene.airlineserver.data.repository.PassengerRepository;
import com.anaene.airlineserver.web.controller.PassengerController;
import com.anaene.airlineserver.web.controller.util.BookingDetails;
import com.anaene.airlineserver.web.controller.util.PaymentCardDetails;
import com.anaene.airlineserver.web.resource.PassengerResourceAssembler;
import com.anaene.airlineserver.web.resource.PassportResourceAssembler;
import com.anaene.airlineserver.web.resource.PaymentCardResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private PassengerResourceAssembler passengerResourceAssembler;
    private final BookingService bookingService;
    private final FlightRepository flightRepository;
    private final PaymentCardResourceAssembler paymentCardResourceAssembler;
    private final PassportResourceAssembler passportResourceAssembler;

    public PassengerService(PassengerRepository passengerRepository, PassengerResourceAssembler passengerResourceAssembler,
                            BookingService bookingService, FlightRepository flightRepository,
                            PaymentCardResourceAssembler paymentCardResourceAssembler,
                            PassportResourceAssembler passportResourceAssembler) {
        this.passengerRepository = passengerRepository;
        this.passengerResourceAssembler = passengerResourceAssembler;
        this.bookingService = bookingService;
        this.flightRepository = flightRepository;
        this.paymentCardResourceAssembler = paymentCardResourceAssembler;
        this.passportResourceAssembler = passportResourceAssembler;
    }

    @Transactional
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Transactional
    public Passenger getPassengerById(long id) {
        return passengerRepository.findPassengerById(id).get();
    }

    @Transactional
    public Resource<Passenger> getPassengerResource(long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();
        return passengerResourceAssembler.toResource(passenger);
    }

    @Transactional
    public Resources<Resource<Booking>> getPassengerBookings(long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();
        Resources<Resource<Booking>> resources = new Resources<>(passenger.getBookings().stream().map(bookingService::getBookingResource).collect(Collectors.toList()));
        resources.add(linkTo(methodOn(PassengerController.class).getPassengerBookings(passengerId)).withSelfRel());
        resources.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passengerId)).withRel("passenger"));
        return resources;
    }

    @Transactional
    public Resource<Booking> getPassengerBooking(long passengerId, long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        Resource<Booking> resource = bookingService.getBookingResource(booking);
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passengerId)).withRel("passenger-account"));
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerBookings(passengerId)).withRel("passenger-bookings"));
        return resource;
    }

    @Transactional
    public Resource<Booking> addPassengerBooking(long passengerId, BookingDetails bookingDetails) {
        Flight flight = flightRepository.findById(bookingDetails.getFlightId()).orElseThrow();
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();
        PaymentCard paymentCard = passenger.getPaymentCards().stream().filter(p -> p.getId() == bookingDetails.getPaymentCardId()).findFirst().orElseThrow();
        Booking booking = new Booking(passenger, flight, LocalDateTime.now(), bookingDetails.getPurchasePrice(), paymentCard);
        booking = bookingService.saveBooking(booking);
        return getPassengerBooking(booking.getPassenger().getId(), booking.getId());
    }

    @Transactional
    public String deleteBooking(long passengerId, long bookingId) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
        LocalDateTime now = LocalDateTime.now();
        if (!now.plusDays(2).isBefore(booking.getPurchaseDate())) throw new Exception("Cancellation period expired");
        if (booking.getPassenger().getId() != passengerId) throw new Exception("Not passenger's booking");
        return "Booking: " + booking.getId() + ", deleted";
    }

    @Transactional
    public Resources<Resource<PaymentCard>> getPaymentCards(long passengerId) {
        Resources<Resource<PaymentCard>> resources = new Resources<>(passengerRepository.findById(passengerId)
                .orElseThrow()
                .getPaymentCards()
                .stream()
                .map(paymentCardResourceAssembler::toResource)
                .collect(Collectors.toList()));
        resources.add(linkTo(methodOn(PassengerController.class).getPassengerPaymentCards(passengerId)).withSelfRel());
        resources.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passengerId)).withRel("passenger-account"));
        return resources;
    }

    @Transactional
    public Resource<PaymentCard> getPaymentCard(long passengerId, long paymentCardId) {
        PaymentCard paymentCard = passengerRepository.findById(passengerId).orElseThrow()
                .getPaymentCards().stream()
                .filter(p -> p.getId() == paymentCardId)
                .findFirst()
                .orElseThrow();
        Resource<PaymentCard> resource = paymentCardResourceAssembler.toResource(paymentCard);
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passengerId)).withRel("passenger-account"));
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerPaymentCards(passengerId)).withRel("payment-cards"));
        return resource;
    }


    @Transactional
    public Resource<PaymentCard> addPaymentCard(long passengerId, PaymentCardDetails paymentCardDetails) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();
        PaymentCard paymentCard = new PaymentCard(paymentCardDetails.getCardNumber(), paymentCardDetails.getName(), paymentCardDetails.getExpiryDate());
        passenger.addPaymentCard(paymentCard);
        passenger = passengerRepository.save(passenger);
        paymentCard = passenger.getPaymentCards().stream().filter(p -> p.getCardName()
                .equals(paymentCardDetails.getName())
                && p.getCardNumber() == paymentCardDetails.getCardNumber()
                && p.getExpiryDate().equals(paymentCardDetails.getExpiryDate()))
                .findFirst()
                .orElseThrow();
        return getPaymentCard(passengerId, paymentCard.getId());
    }

    @Transactional
    public Resource<Passport> getPassengerPassport(long passengerId) {
        Resource<Passport> resource = passportResourceAssembler.toResource(passengerRepository.findById(passengerId).orElseThrow().getPassport());
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerPassport(passengerId)).withSelfRel());
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passengerId)).withRel("passenger-account"));
        return resource;
    }
}
