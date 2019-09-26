package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Client;
import com.anaene.airlineserver.data.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsername(String username);

    Optional<Client> findByPassenger(Passenger p);
}
