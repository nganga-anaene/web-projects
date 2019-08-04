package bookstore.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@ToString(exclude = "book")
public class BookItem {

    @Id
    @GeneratedValue
    private long id;
    private int amount;
    @ManyToOne(optional = false)
    @JsonManagedReference
    private Book book;
    private BigDecimal price;
    private BigDecimal total;

    public BookItem(Book book, int amount) {
        this.book = book;
        this.amount = amount;
        this.price = book.getPrice();
        setTotal();
    }

    private void setTotal() {
        this.total = price.multiply(BigDecimal.valueOf(amount));
    }
}
