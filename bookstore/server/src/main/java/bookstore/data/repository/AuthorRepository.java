package bookstore.data.repository;

import bookstore.data.entity.Author;
import bookstore.data.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByBooksContains(Book book);
}
