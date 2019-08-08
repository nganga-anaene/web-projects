package com.anaene.airlineserver.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"flight", "passenger"})
@ToString(exclude = {"flight", "passenger"})
@NamedEntityGraph(name = "booking.full", attributeNodes = {@NamedAttributeNode("passenger"), @NamedAttributeNode("flight")})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(optional = false)
    @JsonBackReference
    private Passenger passenger;

    @ManyToOne
    @JsonBackReference
    private Flight flight;
    private LocalDateTime purchaseDate;
    private BigDecimal purchasePrice;
    @ManyToOne
    private PaymentCard paymentCard;

    public Booking(Flight flight, LocalDateTime purchaseDate, BigDecimal purchasePrice, PaymentCard paymentCard) {
        this.flight = flight;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.paymentCard = paymentCard;
    }

    public Booking(Passenger passenger, Flight flight, LocalDateTime purchaseDate, BigDecimal purchasePrice, PaymentCard paymentCard) {
        this.passenger = passenger;
        this.flight = flight;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.paymentCard = paymentCard;
    }
}
