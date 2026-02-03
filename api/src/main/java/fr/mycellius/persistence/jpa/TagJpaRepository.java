package fr.mycellius.persistence.jpa;
import fr.mycellius.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByValue(String value);
}