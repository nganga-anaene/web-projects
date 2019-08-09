package com.anaene.airlineserver.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"departingFlights", "arrivalFlights"})
@ToString(exclude = {"departingFlights", "arrivalFlights"})
public class Airport {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String name;
    @OneToOne
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departingAirport", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Flight> departingFlights = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arrivalAirport", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Flight> arrivalFlights = new HashSet<>();

    public Airport(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void addArrivalFlight(Flight flight) {
        this.arrivalFlights.add(flight);
    }

    public void addDepartureFlight(Flight flight) {
        this.departingFlights.add(flight);
    }
}
