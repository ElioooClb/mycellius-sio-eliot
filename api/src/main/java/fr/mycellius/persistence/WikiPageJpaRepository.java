package fr.mycellius.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WikiPageJpaRepository extends JpaRepository<WikiPageEntity, String> {
    List<WikiPageEntity> findByTitleContainingIgnoreCase(String fragment);
}