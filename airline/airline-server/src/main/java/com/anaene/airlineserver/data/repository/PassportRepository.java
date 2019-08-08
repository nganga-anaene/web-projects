package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    Optional<Passport> findByPassportNumber(String passportNumber);
}
