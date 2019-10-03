package bookstore.web.service;

import bookstore.data.entity.Address;
import bookstore.data.entity.Customer;
import bookstore.data.entity.CustomerOrder;
import bookstore.data.entity.util.AppUser;
import bookstore.data.entity.util.OrderStatus;
import bookstore.data.repository.AddressRepository;
import bookstore.data.repository.CustomerOrderRepository;
import bookstore.data.repository.CustomerRepository;
import bookstore.web.controller.AccountController;
import bookstore.web.exception.CustomerNotFoundException;
import bookstore.web.exception.CustomerOrderNotFoundException;
import bookstore.web.resource.AddressResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final AddressRepository addressRepository;
    private final AddressResourceAssembler addressResourceAssembler;

    public CustomerService(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository,
                           AddressRepository addressRepository,
                           AddressResourceAssembler addressResourceAssembler) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.addressRepository = addressRepository;
        this.addressResourceAssembler = addressResourceAssembler;
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

    @Transactional
    public Address getAddress(long id) {
        return addressRepository.findById(id).get();
    }

    public Resource<Address> getAddressResource(long id) {
        Address address = getAddress(id);
        Resource<Address> resource = addressResourceAssembler.toResource(address);
        resource.add(linkTo(methodOn(AccountController.class).getCustomerAddresses()).withRel("addresses"));
        return resource;
    }

    public Resources<Resource<Address>> getCustomerAddressResources(Customer customer) {
        List<Resource<Address>> list = customer.getAddresses().stream().map(addressResourceAssembler::toResource).collect(Collectors.toList());
        Resources<Resource<Address>> resources = new Resources<>(list);
        resources.add(linkTo(methodOn(AccountController.class).getCustomerAddresses()).withSelfRel());
        return  resources;
    }
}
