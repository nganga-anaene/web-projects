package com.anaene.airlineserver.web.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {

    @NotNull
    private long flightId;
    @NotNull
    private long paymentCardId;
    @NotNull
    private BigDecimal purchasePrice;
}
