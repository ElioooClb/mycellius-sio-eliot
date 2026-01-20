package fr.mycellius.repository;
import fr.mycellius.domain.WikiPage;
import java.util.List;
public interface WikiRepository {
    WikiPage save(WikiPage page);
    boolean existsById(String id);
    WikiPage getById(String id);
    List<WikiPage> searchByTitleContaining(String fragment);
    List<WikiPage> findAll();
}