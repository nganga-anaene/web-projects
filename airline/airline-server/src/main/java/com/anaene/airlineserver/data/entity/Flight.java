package com.anaene.airlineserver.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"departingAirport", "arrivalAirport"})
public class Flight {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Airport departingAirport;
    @ManyToOne
    private Airport arrivalAirport;
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime arrivalTime;
    private BigDecimal initialPrice = BigDecimal.valueOf(400);
    @Transient
    private final int maxBookings = 416;
    @ManyToMany(mappedBy = "flights", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Booking> bookings = new HashSet<>();

    public Flight(Airport departingAirport, Airport arrivalAirport, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.departingAirport = departingAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @JsonInclude
    public int currentBookings() {
        return bookings.size();
    }
}
