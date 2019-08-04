package bookstore.server.config;

import bookstore.data.entity.*;
import bookstore.data.entity.util.AppUser;
import bookstore.data.entity.util.Name;
import bookstore.data.entity.util.OrderStatus;
import bookstore.data.repository.*;
import bookstore.server.util.DataGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
public class LoadData {

    private CustomerRepository customerRepository;
    private BookRepository bookRepository;
    private DataGenerator generator;
    private ApplicationContext context;
    private BookItemRepository bookItemRepository;

    @Bean
    CommandLineRunner loadBooks(BookRepository bookRepository, DataGenerator generator, ApplicationContext context) {
        this.bookRepository = bookRepository;
        this.generator = generator;
        this.context = context;
        return args -> bookRepository.saveAll(setBooks(generator));
    }

    //add admin
    @Bean
    CommandLineRunner loadAppAdministrators(AdminRepository repository) {
        return args -> {
            repository.save(new AppAdmin(new Name("Robert", "Baratheon"), new AppUser("drunkking", "robert@kingslanding.com", "password1")));
            repository.save(new AppAdmin(new Name("Sean", "Bean"), new AppUser("firsttodie", "seanbean@typecast.com", "password1")));
            repository.save(new AppAdmin(new Name("Steve", "Ballmer"), new AppUser("energy", "wooooooooooo@microsft.com", "password1")));
        };
    }

    //add customers
    @Bean
    CommandLineRunner loadCustomers(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        return args -> {
            customerRepository.save(new Customer(new Name("Pablo", "Escobar"),
                    new AppUser("escobar", "pabloescobar@colombia.com", encodedPassword()),
                    getAddress(generator)));
            customerRepository.save(new Customer(new Name("Walter", "White"),
                    new AppUser("heisenberg", "walterw@usa.com", encodedPassword()),
                    getAddress(generator)));
            customerRepository.save(new Customer(new Name("Gustavo", "Fring"),
                    new AppUser("chickenman", "lospollos.management@usa.com", encodedPassword()),
                    getAddress(generator)));
            customerRepository.save(new Customer(new Name("Tony", "Soprano"),
                    new AppUser("leadbelly", "tsoprano@usa.com", encodedPassword()),
                    getAddress(generator)));
            customerRepository.save(new Customer(new Name("Thomas", "Shelby"),
                    new AppUser("tommy", "tshelby@uk.com", encodedPassword()),
                    getAddress(generator)));
            customerRepository.save(new Customer(new Name("Hannibal", "Lecter"),
                    new AppUser("hannibal", "helloclarice@suprise.com", encodedPassword()),
                    getAddress(generator)));
        };
    }

    @Bean
    CommandLineRunner setBookItems(BookItemRepository bookItemRepository) {
        this.bookItemRepository = bookItemRepository;
        return args -> bookRepository.findAll()
                .forEach(book -> {
                    for (int i = 0; i < 5; i++) {
                        bookItemRepository.save(new BookItem(book, i + 1));
                    }
                });
    }

    @Bean
    CommandLineRunner setCustomerOrders(CustomerOrderRepository customerOrderRepository) {
        return args -> customerRepository.findAll()
                .forEach(customer -> {
                    try {
                        customer = customerRepository.findCustomerAddressesById(customer.getId()).orElseThrow(() -> new Exception("Customer not found"));

                        Address address = customer.getAddresses().stream().findFirst().orElseThrow(() -> new EntityNotFoundException("NO Address found"));
                        Random r = new Random();
                        List<Book> books = bookRepository.findAll();
                        for (int i = 0; i < r.nextInt(10) + 1; i++) {
                            CustomerOrder order = new CustomerOrder(customer, address);
                            order.setBookItems(getBookItems(books));
                            order.setOrderStatus(OrderStatus.COMPLETED);
                            customerOrderRepository.save(order);
                        }
                    } catch (Exception e) {
                    }
                });
    }

    private Set<BookItem> getBookItems(List<Book> books) {
        Random r = new Random();
        Set<BookItem> bookItems = new HashSet<>();
        for (int i = 0; i < r.nextInt(5) + 1; i++) {
            Book book = books.get(r.nextInt(books.size() - 1));
            List<BookItem> items = bookItemRepository.findByBook(book);
            bookItems.add(items.get(r.nextInt(items.size() - 1)));
        }
        return bookItems;
    }


    private Address getAddress(DataGenerator generator) {
        return new Address(generator.getStreetName(), generator.getPostcode(), generator.getCity());
    }


    private Publisher getPublisher(DataGenerator generator) {
        return new Publisher(generator.getPublisherName(), getAddress(generator));
    }

    private List<Book> setBooks(DataGenerator generator) {
        List<Book> books = new ArrayList<>();
        Map<String, String> bookImages = generator.getBookImages();
        generator.getBookAuthors().forEach((title, authorName) -> {
            Author author = new Author(authorName);
            Publisher publisher = getPublisher(generator);
            URL imageUrl = getBookUrl(bookImages.get(title));
            books.add(new Book(title, generator.getSynopsis(), generator.setPrice(), author, publisher, imageUrl));
        });
        return books;
    }

    private String encodedPassword() {
        return context.getBean(SecurityConfiguration.class).encoder().encode("password1");
    }

    public URL getBookUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
