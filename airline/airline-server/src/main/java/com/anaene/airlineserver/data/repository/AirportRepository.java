package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
