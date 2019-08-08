package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.entity.Passport;
import com.anaene.airlineserver.data.entity.PaymentCard;
import com.anaene.airlineserver.data.entity.util.Gender;
import com.anaene.airlineserver.data.entity.util.Name;
import com.anaene.airlineserver.data.entity.util.Person;
import com.anaene.airlineserver.data.repository.PassengerRepository;
import com.anaene.airlineserver.data.repository.PassportRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class LoadPassengers {

    private final PassengerRepository passengerRepository;
    private final AppUtils appUtils;
    private final PassportRepository passportRepository;
    private List<String> maleNames = new ArrayList<>();
    private List<String> femaleNames = new ArrayList<>();
    private List<String> surnames = new ArrayList<>();

    LoadPassengers(PassengerRepository passengerRepository, AppUtils appUtils, NameReader nameReader, PassportRepository passportRepository) {
        this.passengerRepository = passengerRepository;
        this.appUtils = appUtils;
        this.passportRepository = passportRepository;
        maleNames.addAll(nameReader.getMaleNames());
        femaleNames.addAll(nameReader.getFemaleNames());
        surnames.addAll(nameReader.getSurnames());
    }

    public void addPassengers() {
        for (long i = 0; i < 100; i++) {
            Passenger passenger = new Passenger(getPassport());
            passenger = passengerRepository.save(passenger);
            passenger.addPaymentCard(getPaymentCard(passenger.getPassport().getPerson()));
            passengerRepository.save(passenger);
        }
    }

    private Passport getPassport() {
        boolean passportFound = true;
        String passportNumber = "";
        while (passportFound) {
            passportNumber = appUtils.getPassportNumber();
            passportFound = passportFound(passportNumber);
        }
        return new Passport(passportNumber, getPerson(appUtils.getGender()), appUtils.getExpiryDate());
    }

    private Person getPerson(Gender gender) {
        Random r = new Random();
        String firstName = (gender == Gender.FEMALE) ?
                femaleNames.get(r.nextInt(femaleNames.size()))
                : maleNames.get(r.nextInt(maleNames.size()));
        String lastName = surnames.get(r.nextInt(surnames.size()));
        return new Person(new Name(firstName, lastName), appUtils.getDateOfBirth(r.nextInt(40) + 20), "Some City", gender);
    }


    private boolean passportFound(String passportNumber) {
        try {
            passportRepository.findByPassportNumber(passportNumber).orElseThrow(() -> new EntityNotFoundException(passportNumber));
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    private PaymentCard getPaymentCard(Person person) {
        String title = setTitle(person.getGender());
        String name = title + " " + person.getName().getFirstName() + " " + person.getName().getLastName();
        String cardNumber = setCardNumber();
        return new PaymentCard(Long.valueOf(cardNumber), name.toUpperCase(), setExpiryDate());
    }

    private LocalDate setExpiryDate() {
        Random r = new Random();
        return LocalDate.now().plusYears(r.nextInt(4) + 1);
    }

    private String setCardNumber() {
        Random r = new Random();
        int cardNumberLength = r.nextInt(4) + 13;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cardNumberLength; i++) {
            builder.append(r.nextInt(10));
        }
        return builder.toString();
    }

    private String setTitle(Gender gender) {
        Random r = new Random();
        if (gender == Gender.FEMALE) {
            String[] titles = new String[]{"MS", "MRS", "MISS"};
            return titles[r.nextInt(titles.length)];
        } else {
            return "MR";
        }
    }
}
