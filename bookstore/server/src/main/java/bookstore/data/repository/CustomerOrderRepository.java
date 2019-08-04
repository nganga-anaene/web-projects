package bookstore.data.repository;

import bookstore.data.entity.Customer;
import bookstore.data.entity.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    @EntityGraph(attributePaths = {"customer", "bookItems"})
    Page<CustomerOrder> findByCustomer(Customer customer, Pageable pageable);

    Optional<CustomerOrder> findByCustomerAndId(Customer customer, long id);
}
