package bookstore.data.repository;

import bookstore.data.entity.Book;
import bookstore.data.entity.BookItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookItemRepository extends JpaRepository<BookItem, Long> {

    @EntityGraph(attributePaths = "book")
    Optional<BookItem> findByBookAndAmount(Book book, int amount);

    @EntityGraph(attributePaths = "book")
    List<BookItem> findByBook(Book book);

    @EntityGraph(attributePaths = "book")
    Optional<BookItem> findByBookAndId(Book book, Long id);

    List<BookItem> findByIdIn(List<Long> id);
}
