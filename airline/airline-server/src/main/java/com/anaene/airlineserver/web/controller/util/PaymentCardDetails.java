package com.anaene.airlineserver.web.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardDetails {

    private long cardNumber;
    private String name;
    private LocalDate expiryDate;
}
