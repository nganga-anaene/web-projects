package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.entity.Passport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @EntityGraph("passenger.full")
    Optional<Passenger> findPassengerById(long id);

    @EntityGraph("passenger.full")
    Optional<Passenger> findByPassport(Passport passport);
}
