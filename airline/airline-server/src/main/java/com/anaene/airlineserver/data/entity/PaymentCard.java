package com.anaene.airlineserver.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cardNumber", "cardName", "expiryDate"}))
public class PaymentCard {

    @Id
    @GeneratedValue
    private long id;

    private long cardNumber;
    private String cardName;
    private LocalDate expiryDate;

    public PaymentCard(long cardNumber, String cardName, LocalDate expiryDate) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
    }
}
