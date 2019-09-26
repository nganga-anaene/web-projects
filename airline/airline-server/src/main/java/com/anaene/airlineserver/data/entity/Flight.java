package com.anaene.airlineserver.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private BigDecimal price;
    @Transient
    private final int maxBookings = 416;
    @Transient
    private int currentBookings;
    @ManyToMany(mappedBy = "flights", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Booking> bookings = new HashSet<>();

    public Flight(Airport departingAirport, Airport arrivalAirport, LocalDateTime departureTime, LocalDateTime arrivalTime, BigDecimal price) {
        this.departingAirport = departingAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }
}
