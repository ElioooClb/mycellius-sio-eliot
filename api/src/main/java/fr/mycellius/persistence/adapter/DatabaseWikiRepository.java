package fr.mycellius.persistence.adapter;
import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.exception.PageNotFoundException;
import fr.mycellius.persistence.entity.TagEntity;
import fr.mycellius.persistence.entity.WikiPageEntity;
import fr.mycellius.persistence.jpa.TagJpaRepository;
import fr.mycellius.persistence.WikiPageJpaRepository;
import fr.mycellius.persistence.mapper.WikiPageEntityMapper;
import fr.mycellius.repository.WikiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Repository
public class DatabaseWikiRepository implements WikiRepository {
    private final WikiPageJpaRepository pageJpa;
    private final TagJpaRepository tagJpa;
    private final WikiPageEntityMapper mapper;
    public DatabaseWikiRepository(WikiPageJpaRepository pageJpa,
                                  TagJpaRepository tagJpa,
                                  WikiPageEntityMapper mapper) {
        this.pageJpa = pageJpa;
        this.tagJpa = tagJpa;
        this.mapper = mapper;
    }
    @Override
    public void deleteById(String id) {
        if (!pageJpa.existsById(id)) {
            throw new PageNotFoundException(id);
        }
        pageJpa.deleteById(id);
    }
    @Override
    public WikiPage save(WikiPage page) {
        WikiPageEntity entity = mapper.toEntity(page);
        // createdAt côté DB : si absent on le fixe maintenant
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());
        }
        // tags : "find or create" (normalisation + dédoublonnage)
        List<Tag> pageTags = (page.getTags() == null) ? List.of() : page.getTags();
        Set<TagEntity> resolvedTags = pageTags.stream()
                .map(Tag::getValue)
                .map(v -> v == null ? "" : v.trim())
                .filter(v -> !v.isBlank())
                .map(String::toLowerCase)
                .distinct()
                .map(this::findOrCreateTag)
                .collect(Collectors.toSet());
        entity.setTags(resolvedTags);
        WikiPageEntity saved = pageJpa.save(entity);
        return mapper.toDomain(saved);
    }
    private TagEntity findOrCreateTag(String value) {
        return tagJpa.findByValue(value)
                .orElseGet(() -> tagJpa.save(new TagEntity(value)));
    }
    @Override
    public WikiPage getById(String id) {
        return pageJpa.findById(id).map(mapper::toDomain).orElse(null);
    }
    @Override
    public Page<WikiPage> findAll(Pageable pageable) {
        return pageJpa.findAll(pageable).map(mapper::toDomain);
    }
    @Override
    public Page<WikiPage> searchByTitle(String title, Pageable pageable) {
        return pageJpa.findByTitleContainingIgnoreCase(title, pageable).map(mapper::toDomain);
    }
}