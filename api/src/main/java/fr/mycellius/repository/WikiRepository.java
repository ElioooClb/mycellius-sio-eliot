package fr.mycellius.repository;

import fr.mycellius.domain.WikiPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WikiRepository {

    WikiPage save(WikiPage page);

    WikiPage getById(String id);

    Page<WikiPage> findAll(Pageable pageable);

    Page<WikiPage> findAllPublic(Pageable pageable);

    Page<WikiPage> searchByTitle(String title, Pageable pageable);

    Page<WikiPage> searchPublicByTitle(String title, Pageable pageable);

    void deleteById(String id);
}