package com.anaene.airlineserver.web.controller.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDetails {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String passportNumber;
    private String passportExpiryDate;
    private long paymentCardNumber;
    private String paymentCardName;
    private String paymentCardExpiryDate;

}
