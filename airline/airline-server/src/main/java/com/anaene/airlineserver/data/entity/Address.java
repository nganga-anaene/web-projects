package com.anaene.airlineserver.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"street", "city", "postcode", "country"}))
public class Address {

    @Id
    @GeneratedValue
    private long id;
    private String street;
    private String city;
    private String postcode;
    private String country;

    public Address(String street, String city, String postcode, String country){
        this.street = street;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }
}
