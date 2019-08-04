package bookstore.data.repository;

import bookstore.data.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book.author", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findBookAuthorByIsbn(String isbn);

    @EntityGraph(value = "book.publisher", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findBookPublisherByIsbn(String isbn);

    @EntityGraph(attributePaths = "bookItems")
    Optional<Book> findBookItemsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    @EntityGraph(value = "book.full")
    Optional<Book> findFullBookById(Long id);
}
