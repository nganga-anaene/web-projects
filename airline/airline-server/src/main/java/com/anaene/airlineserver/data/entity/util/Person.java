package com.anaene.airlineserver.data.entity.util;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Embedded
    @NotNull
    @JsonUnwrapped
    private Name name;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private String placeOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
