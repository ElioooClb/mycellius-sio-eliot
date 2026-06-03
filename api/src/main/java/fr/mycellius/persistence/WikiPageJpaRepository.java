package fr.mycellius.persistence;

import fr.mycellius.persistence.entity.WikiPageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiPageJpaRepository
        extends JpaRepository<WikiPageEntity, String> {

    Page<WikiPageEntity> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable);

    Page<WikiPageEntity> findByConfidentialFalse(
            Pageable pageable);

    Page<WikiPageEntity> findByTitleContainingIgnoreCaseAndConfidentialFalse(
            String title,
            Pageable pageable);
}