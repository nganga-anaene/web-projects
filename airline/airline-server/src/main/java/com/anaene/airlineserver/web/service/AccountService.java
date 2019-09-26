package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Client;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.entity.Passport;
import com.anaene.airlineserver.data.entity.PaymentCard;
import com.anaene.airlineserver.data.entity.util.Gender;
import com.anaene.airlineserver.data.entity.util.Name;
import com.anaene.airlineserver.data.entity.util.Person;
import com.anaene.airlineserver.data.repository.ClientRepository;
import com.anaene.airlineserver.web.controller.util.RegistrationDetails;
import org.springframework.hateoas.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class AccountService {

    private final ClientRepository clientRepository;
    private final PassengerService passengerService;

    public AccountService(ClientRepository clientRepository, PassengerService passengerService) {
        this.clientRepository = clientRepository;
        this.passengerService = passengerService;
    }

    @Transactional
    public Resource<Passenger> addClient(RegistrationDetails registrationDetails) {
        long passengerId = processRegistration(registrationDetails);
        return passengerService.getPassengerResource(passengerId);
    }

    @Transactional
    long processRegistration(RegistrationDetails registrationDetails) {
        Name name = new Name(registrationDetails.getFirstName(), registrationDetails.getLastName());
        Person person = new Person(name, parseDate(registrationDetails.getDateOfBirth()), registrationDetails.getPlaceOfBirth(), Gender.valueOf(registrationDetails.getGender()));
        Passport passport = new Passport(registrationDetails.getPassportNumber(), person, parseDate(registrationDetails.getPassportExpiryDate()));
        PaymentCard paymentCard = new PaymentCard(registrationDetails.getPaymentCardNumber(), registrationDetails.getPaymentCardName(), parseDate(registrationDetails.getPaymentCardExpiryDate()));
        Passenger passenger = passengerService.savePassenger(new Passenger(passport, paymentCard));
        Client client = new Client(name, registrationDetails.getUsername(), passwordEncoder().encode(registrationDetails.getPassword()), passenger);
        client = clientRepository.save(client);
        return client.getPassenger().getId();
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    @Transactional
    public long getPassengerId(String username) {
        return clientRepository.findByUsername(username).orElseThrow().getPassenger().getId();
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public boolean findUser(String username) {
        return clientRepository.findByUsername(username).isPresent();
    }
}
