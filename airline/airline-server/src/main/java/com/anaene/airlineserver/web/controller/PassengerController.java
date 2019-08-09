package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.entity.Passport;
import com.anaene.airlineserver.data.entity.PaymentCard;
import com.anaene.airlineserver.web.controller.util.BookingDetails;
import com.anaene.airlineserver.web.controller.util.PaymentCardDetails;
import com.anaene.airlineserver.web.service.PassengerService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account/{passengerId}")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("")
    public ResponseEntity<Resource<Passenger>> getPassengerDetails(@PathVariable long passengerId) {
        try {
            Resource<Passenger> resource = passengerService.getPassengerResource(passengerId);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<Resources<Resource<Booking>>> getPassengerBookings(@PathVariable long passengerId) {
        try {
            Resources<Resource<Booking>> resources = passengerService.getPassengerBookings(passengerId);
            return ResponseEntity.ok(resources);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Resource<Booking>> getPassengerBooking(@PathVariable long passengerId, @PathVariable long bookingId) {
        try {
            Resource<Booking> resource = passengerService.getPassengerBooking(passengerId, bookingId);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("bookings/add-booking")
    public ResponseEntity<Resource<Booking>> addPassengerBooking(@PathVariable long passengerId, @RequestBody BookingDetails bookingDetails) {
        try {
            return ResponseEntity.ok(passengerService.addPassengerBooking(passengerId, bookingDetails));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("bookings/delete-booking/{bookingId}")
    public ResponseEntity cancelPassengerBooking(@PathVariable long passengerId, @PathVariable long bookingId) {
        try {
            return ResponseEntity.ok(passengerService.deleteBooking(passengerId, bookingId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("payment-cards")
    public ResponseEntity<Resources<Resource<PaymentCard>>> getPassengerPaymentCards(@PathVariable long passengerId) {
        try {
            return ResponseEntity.ok(passengerService.getPaymentCards(passengerId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("payment-cards/{paymentCardId}")
    public ResponseEntity<Resource<PaymentCard>> getPaymentCard(@PathVariable long passengerId, @PathVariable long paymentCardId) {
        try {
            return ResponseEntity.ok(passengerService.getPaymentCard(passengerId, paymentCardId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("payment-cards/add-payment-card")
    public ResponseEntity<Resource<PaymentCard>> addPaymentCard(long passengerId, @RequestBody PaymentCardDetails paymentCardDetails) {
        try {
            return ResponseEntity.ok(passengerService.addPaymentCard(passengerId, paymentCardDetails));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("passport")
    public ResponseEntity<Resource<Passport>> getPassengerPassport(@PathVariable long passengerId) {
        try{
            return ResponseEntity.ok(passengerService.getPassengerPassport(passengerId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
