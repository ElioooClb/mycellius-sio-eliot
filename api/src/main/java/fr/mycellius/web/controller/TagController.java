package fr.mycellius.web.controller;
import fr.mycellius.persistence.entity.TagEntity;
import fr.mycellius.persistence.jpa.TagJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TagController {
    private final TagJpaRepository tagJpa;
    public TagController(TagJpaRepository tagJpa) {
        this.tagJpa = tagJpa;
    }
    @GetMapping("/api/v1/tags")
    public List<String> listTags() {
        return tagJpa.findAll().stream()
                .map(TagEntity::getValue)
                .toList();
    }
}