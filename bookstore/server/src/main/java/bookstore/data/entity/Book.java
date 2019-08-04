package bookstore.data.entity;

import bookstore.data.entity.util.Name;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"author", "publisher"})
@ToString(exclude = {"author", "publisher"})
@NamedEntityGraph(name = "book.author", attributeNodes = @NamedAttributeNode("author"))
@NamedEntityGraph(name = "book.publisher", attributeNodes = @NamedAttributeNode("publisher"))
@NamedEntityGraph(name = "book.items", attributeNodes = @NamedAttributeNode("bookItems"))
@NamedEntityGraph(name = "book.full", attributeNodes = {@NamedAttributeNode("author"),
        @NamedAttributeNode("publisher"),
        @NamedAttributeNode("bookItems")})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(unique = true)
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    @Size(max = 300)
    private String synopsis;

    private URL imageUrl;

    private BigDecimal price;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Author author;

    @JsonUnwrapped
    private Name authorName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Publisher publisher;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonBackReference
    @OneToMany(mappedBy = "book", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BookItem> bookItems = new ArrayList<>();

    public Book(@NotNull String title, @NotNull @Size(max = 300) String synopsis, BigDecimal price, Author author, Publisher publisher, URL imageUrl) {
        this.title = title;
        this.synopsis = synopsis;
        this.price = price;
        this.author = author;
        this.authorName = author.getName();
        this.publisher = publisher;
        this.imageUrl = imageUrl;
    }
}
