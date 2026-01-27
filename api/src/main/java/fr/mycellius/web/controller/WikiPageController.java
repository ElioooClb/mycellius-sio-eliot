package fr.mycellius.web.controller;
import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.service.WikiService;
import fr.mycellius.web.CreatePageRequest;
import fr.mycellius.web.dto.TagRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import fr.mycellius.web.dto.CreateWikiPageRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pages")
public class WikiPageController {
    private final WikiService wikiService;
    public WikiPageController(WikiService wikiService) {
        this.wikiService = wikiService;
    }
    @PostMapping
    public ResponseEntity<WikiPage> createPage(@Valid @RequestBody CreateWikiPageRequest request) {
        List<Tag> domainTags = toDomainTags(request.tags());
        WikiPage page = wikiService.createPage(
                request.id(),
                request.title(),
                request.content(),
                domainTags
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(page);
    }
    @GetMapping("/{id}")
    public WikiPage getPageById(@PathVariable String id) {
        return wikiService.getPageById(id);
    }

    @GetMapping("/search")
    public List<WikiPage> searchByTitle(@RequestParam("title") String fragment) {
        return wikiService.searchByTitle(fragment);
    }
    private List<Tag> toDomainTags(List<TagRequest> tags) {
        if (tags == null) return List.of();
        return tags.stream()
                .map(TagRequest::name)
                .map(Tag::new)
                .toList();
    }
}