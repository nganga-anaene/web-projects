package com.anaene.airlineserver.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Passenger passenger;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Flight> flights = new HashSet<>();
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private LocalDateTime purchaseDate;
    private BigDecimal purchasePrice;
    @ManyToOne(cascade = CascadeType.ALL)
    private PaymentCard paymentCard;

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