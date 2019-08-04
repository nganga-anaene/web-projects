package bookstore.data.entity;

import bookstore.data.entity.util.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@ToString(exclude = {"customer", "bookItems"})
public class CustomerOrder {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JsonBackReference
    private Customer customer;
    @OneToMany
    @JoinColumn
    @JsonManagedReference
    private Set<BookItem> bookItems = new HashSet<>();
    private BigDecimal total = BigDecimal.ZERO;
    @ManyToOne
    private Address shippingAddress;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PROCESSING;

    public CustomerOrder(Customer customer, Address address) {
        this.customer = customer;
        this.shippingAddress = address;
    }

    public CustomerOrder(Customer customer, Address address, Set<BookItem> bookItems) {
        this.customer = customer;
        this.shippingAddress = address;
        this.bookItems = bookItems;
        setTotal();
    }

    public CustomerOrder(Customer customer, Address address, BookItem bookItem) {
        this.customer = customer;
        addBookItem(bookItem);
    }

    public void addBookItem(BookItem item) {
        bookItems.add(item);
        setTotal();
    }

    private void setTotal() {
        total = bookItems.stream().map(BookItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void processOrder() {
        orderStatus = OrderStatus.COMPLETED;
    }

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELLED;
    }
}
