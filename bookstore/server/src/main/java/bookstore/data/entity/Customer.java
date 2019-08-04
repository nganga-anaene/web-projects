package bookstore.data.entity;

import bookstore.data.entity.util.AppUser;
import bookstore.data.entity.util.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"addresses"})
@ToString(exclude = {"addresses"})
@JsonIgnoreProperties({"customerOrders", "addresses"})
public class Customer {

    @Id
    @GeneratedValue
    private long id;
    @Embedded
    @JsonUnwrapped
    private Name name;
    @Embedded
    @JsonUnwrapped
    private AppUser user;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    public Customer(Name name, AppUser user) {
        this.name = name;
        this.user = user;
    }

    public Customer(Name name, AppUser user, Address address) {
        this.name = name;
        this.user = user;
        addAddress(address);
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addOrder(CustomerOrder customerOrder) {
        customerOrders.add(customerOrder);
    }

    public void addAddresses(Set<Address> addresses) {
        this.addresses.addAll(addresses);
    }
}
