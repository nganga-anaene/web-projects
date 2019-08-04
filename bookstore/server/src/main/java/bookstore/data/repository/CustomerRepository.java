package bookstore.data.repository;

import bookstore.data.entity.Customer;
import bookstore.data.entity.util.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = {"addresses"})
    Optional<Customer> findCustomerAddressesById(long id);

    Optional<Customer> findByUserUsername(String username);

    boolean existsByUser(AppUser user);
}
