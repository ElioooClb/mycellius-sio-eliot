package fr.mycellius.repository;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.persistence.WikiPageEntity;
import fr.mycellius.persistence.WikiPageJpaRepository;
import fr.mycellius.domain.exception.PageNotFoundException;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DatabaseWikiRepository implements WikiRepository {
    private final WikiPageJpaRepository jpa;
    public DatabaseWikiRepository(WikiPageJpaRepository jpa) {
        this.jpa = jpa;
    }
    @Override
    public WikiPage save(WikiPage page) {
        WikiPageEntity entity = new WikiPageEntity(
                page.getId(),
                page.getTitle(),
                page.getContent()
        );
        WikiPageEntity saved = jpa.save(entity);
        return new WikiPage(
                saved.getId(),
                saved.getTitle(),
                saved.getContent()
        );
    }
    @Override
    public boolean existsById(String id) {
        return jpa.existsById(id);
    }
    @Override
    public WikiPage getById(String id) {
        return jpa.findById(id)
                .map(e -> new WikiPage(e.getId(), e.getTitle(), e.getContent()))
                .orElseThrow(() -> new PageNotFoundException(id));
    }
    @Override
    public List<WikiPage> searchByTitleContaining(String fragment) {
        return jpa.findByTitleContainingIgnoreCase(fragment)
                .stream()
                .map(e -> new WikiPage(e.getId(), e.getTitle(), e.getContent()))
                .collect(Collectors.toList());
    }
    @Override
    public List<WikiPage> findAll() {
        return jpa.findAll()
                .stream()
                .map(e -> new WikiPage(e.getId(), e.getTitle(), e.getContent()))
                .collect(Collectors.toList());
    }
}