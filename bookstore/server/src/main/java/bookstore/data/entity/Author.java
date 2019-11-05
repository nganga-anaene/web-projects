package bookstore.data.entity;

import bookstore.data.entity.util.Name;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "books")
@ToString(exclude = "books")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Name name;
    private String penName;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    public Author(Name name, String penName) {
        this.name = name;
        this.penName = penName;

    }

    public Author(Name name) {
        this.name = name;
        this.penName = "";
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
