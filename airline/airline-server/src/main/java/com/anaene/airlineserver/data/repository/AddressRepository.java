package com.anaene.airlineserver.data.repository;

import com.anaene.airlineserver.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
