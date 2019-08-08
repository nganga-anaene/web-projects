package com.anaene.airlineserver.data.entity;

import com.anaene.airlineserver.data.entity.util.Person;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String passportNumber;
    @Embedded
    @JsonUnwrapped
    private Person person;
    private LocalDate expiryDate;

    public Passport(String passportNumber, Person person, LocalDate expiryDate){
        this.passportNumber = passportNumber;
        this.person = person;
        this.expiryDate = expiryDate;
    }

}
