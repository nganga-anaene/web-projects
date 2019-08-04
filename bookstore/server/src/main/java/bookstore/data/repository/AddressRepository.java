package bookstore.data.repository;

import bookstore.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAndPostcodeAndCity(String street, String postcode, String city);
}
