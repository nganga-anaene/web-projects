package bookstore.data.repository;

import bookstore.data.entity.AppAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AppAdmin, Long> {
}
