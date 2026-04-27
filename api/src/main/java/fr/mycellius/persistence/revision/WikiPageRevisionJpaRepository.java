package fr.mycellius.persistence.revision;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WikiPageRevisionJpaRepository extends
JpaRepository<WikiPageRevisionEntity, Long> {
 List<WikiPageRevisionEntity> findByPageIdOrderByCreatedAtDesc(String pageId);
}