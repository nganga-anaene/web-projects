package bookstore.web.service;

import bookstore.data.entity.Address;
import bookstore.data.entity.Customer;
import bookstore.data.entity.CustomerOrder;
import bookstore.data.entity.util.AppUser;
import bookstore.data.entity.util.OrderStatus;
import bookstore.data.repository.AddressRepository;
import bookstore.data.repository.CustomerOrderRepository;
import bookstore.data.repository.CustomerRepository;
import bookstore.server.config.SecurityConfiguration;
import bookstore.web.exception.CustomerNotFoundException;
import bookstore.web.exception.CustomerOrderNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final AddressRepository addressRepository;
    private final SecurityConfiguration config;

    public CustomerService(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository, AddressRepository addressRepository, SecurityConfiguration config) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.addressRepository = addressRepository;
        this.config = config;
    }

    public Customer findCustomerByUsername(String username) throws CustomerNotFoundException {
        return customerRepository.findByUserUsername(username).orElseThrow(() -> new CustomerNotFoundException(username));
    }

    @Transactional
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional
    public Page<CustomerOrder> getCustomerOrders(Customer customer, int page, int size) {
        return customerOrderRepository.findByCustomer(customer, PageRequest.of(page, size));
    }

    @Transactional
    public Page<CustomerOrder> getCustomerOrders(Customer customer) {
        return customerOrderRepository.findByCustomer(customer, PageRequest.of(0, 5));
    }

    @Transactional
    public Set<Address> getCustomerAddresses(String username) {
        return customerRepository.findByUserUsername(username).orElseThrow(() -> new CustomerNotFoundException(username)).getAddresses();
    }

    @Transactional
    public CustomerOrder getOrdersByCustomerAndId(Customer customer, long id) {
        return customerOrderRepository.findByCustomerAndId(customer, id).orElseThrow(() -> new CustomerOrderNotFoundException(customer.getUser().getUsername(), id));
    }

    @Transactional
    public CustomerOrder getCustomerOrderById(long id) {
        return customerOrderRepository.findById(id).orElseThrow(() -> new CustomerOrderNotFoundException(id));
    }

    public CustomerOrder saveCustomerOrder(@NonNull final CustomerOrder order) {
        return customerOrderRepository.save(order);
    }

    public CustomerOrder cancelOrder(CustomerOrder customerOrder) {
        customerOrder.setOrderStatus(OrderStatus.CANCELLED);
        return customerOrderRepository.save(customerOrder);
    }

    public void deleteCustomerOrder(CustomerOrder customerOrder) {
        customerOrderRepository.delete(customerOrder);
    }

    public Address getAddress(Address address) {
        return addressRepository
                .findByStreetAndPostcodeAndCity(address.getStreet(), address.getPostcode(), address.getCity())
                .orElseGet(() -> addressRepository.save(address));
    }


    public Customer saveUserDetails(AppUser user) {
        if (customerRepository.existsByUser(user)) throw new EntityExistsException();
        System.out.println(user);
//        user.setPassword(encoder().encode(user.getPassword()));
        Customer customer = new Customer();
        customer.setUser(user);
        return customerRepository.save(customer);
    }


    public Customer saveCompleteCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new CustomerOrderNotFoundException(customer.getId()));
        if (customer.getName() != null) {
            savedCustomer.setName(customer.getName());
        }
        if (!customer.getAddresses().isEmpty()) {
            savedCustomer.addAddresses(customer.getAddresses());
        }
        return customerRepository.save(savedCustomer);
    }

    public String encodePassword(String password) {
        return encoder().encode(password);
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
