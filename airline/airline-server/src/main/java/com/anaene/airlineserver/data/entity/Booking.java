package com.anaene.airlineserver.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"flights", "passenger"})
@ToString(exclude = {"flights", "passenger"})
@NamedEntityGraph(name = "booking.full", attributeNodes = {@NamedAttributeNode("passenger"), @NamedAttributeNode("flights")})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(optional = false)
    private Passenger passenger;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Flight> flights = new HashSet<>();
    private LocalDateTime purchaseDate;
    private BigDecimal purchasePrice;
    @ManyToOne
    private PaymentCard paymentCard;

    public Booking(Set<Flight> flights, LocalDateTime purchaseDate, BigDecimal purchasePrice, PaymentCard paymentCard) {
        this.flights = flights;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.paymentCard = paymentCard;
    }

    public Booking(Passenger passenger, Set<Flight> flights, LocalDateTime purchaseDate, BigDecimal purchasePrice, PaymentCard paymentCard) {
        this.passenger = passenger;
        this.flights = flights;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.paymentCard = paymentCard;
    }

    public Booking(Passenger passenger, Flight flight, LocalDateTime purchaseDate, BigDecimal purchasePrice, PaymentCard paymentCard) {
        this.passenger = passenger;
        this.flights.add(flight);
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.paymentCard = paymentCard;
    }
}
