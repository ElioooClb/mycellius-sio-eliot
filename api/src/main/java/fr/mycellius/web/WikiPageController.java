package fr.mycellius.web;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.service.WikiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/pages")
public class WikiPageController {
    private final WikiService wikiService;
    public WikiPageController(WikiService wikiService) {
        this.wikiService = wikiService;
    }
    @PostMapping
    public ResponseEntity<WikiPage> createPage(@RequestBody CreatePageRequest request) {
        WikiPage page = wikiService.createPage(
                request.id(),
                request.title(),
                request.content(),
                request.tags()
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
}