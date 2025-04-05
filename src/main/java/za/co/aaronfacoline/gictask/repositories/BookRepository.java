package za.co.aaronfacoline.gictask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.aaronfacoline.gictask.entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
