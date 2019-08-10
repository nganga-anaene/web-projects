package com.anaene.airlineserver.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@NamedEntityGraph(name = "passenger.full", attributeNodes = {@NamedAttributeNode("paymentCards"), @NamedAttributeNode("bookings"), @NamedAttributeNode("passport")})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Passenger {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<PaymentCard> paymentCards = new HashSet<>();

    public Passenger(Passport passport) {
        this.passport = passport;
    }

    public Passenger(Passport passport, PaymentCard paymentCard) {
        this.passport = passport;
        addPaymentCard(paymentCard);
    }

    public void addPaymentCard(PaymentCard paymentCard) {
        this.paymentCards.add(paymentCard);
    }

    public void addBookings(List<Booking> bookings) {
        bookings.forEach(this::addBooking);
    }

    public void addBooking(Booking booking) {
        booking.setPassenger(this);
        this.bookings.add(booking);
    }
}
