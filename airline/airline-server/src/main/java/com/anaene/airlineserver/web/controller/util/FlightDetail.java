package com.anaene.airlineserver.web.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetail {

    @NotNull
    private long arrivalAirportId;
    @NotNull
    private long departureAirportId;
    @NotNull
    private LocalDate flightDate;
}
