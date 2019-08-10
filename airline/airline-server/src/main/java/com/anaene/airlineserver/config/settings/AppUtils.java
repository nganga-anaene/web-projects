package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.util.Gender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Configuration
public class AppUtils {

    @Bean
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public LocalDate getDateOfBirth(int age) {
        Random r = new Random();
        LocalDate localDate = LocalDate.now();
        int year = localDate.plusYears(-age).getYear();
        int month = r.nextInt(12) + 1;
        localDate = LocalDate.of(year, month, localDate.getDayOfMonth());
        return localDate.plusDays(r.nextInt(30));
    }

    public String getPassportNumber() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 13; i++) {
            sb.append(r.nextInt(9));
        }
        return sb.toString();
    }

    public Gender getGender() {
        Random r = new Random();
        return Gender.values()[r.nextInt(Gender.values().length)];
    }

    public LocalDate getExpiryDate() {
        Random r = new Random();
        LocalDate localDate = getCurrentTime().toLocalDate();
        localDate = localDate.plusYears(r.nextInt(10) + 1);
        localDate = localDate.plusMonths(r.nextInt(12) + 1);
        localDate = localDate.plusDays(r.nextInt(30) + 1);
        return localDate;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
