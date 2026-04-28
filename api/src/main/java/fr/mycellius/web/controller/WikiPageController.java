package fr.mycellius.web.controller;

import fr.mycellius.domain.WikiPage;
import fr.mycellius.service.WikiService;
import fr.mycellius.service.RevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import fr.mycellius.web.dto.CreateWikiPageRequest;
import jakarta.validation.Valid;
import fr.mycellius.web.mapper.WikiPageDtoMapper;
import fr.mycellius.web.dto.WikiPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/v1/pages")
public class WikiPageController {
    private final WikiService wikiService;
    private final WikiPageDtoMapper mapper;
    private final RevisionService revisionService;
    public WikiPageController(WikiService wikiService, WikiPageDtoMapper mapper, RevisionService revisionService) {
        this.wikiService = wikiService;
        this.mapper = mapper;
        this.revisionService = revisionService;
    }
    @PutMapping("/{id}")
    public WikiPageResponse updatePage(
            @PathVariable String id,
            @Valid @RequestBody CreateWikiPageRequest request
    ) {
        WikiPage currentPage = wikiService.getPageById(id);
        WikiPage current = wikiService.getPageById(id);

            // récupérer le username depuis Spring Security
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = (auth != null) ? auth.getName() : "SYSTEM";

            // enregistrer snapshot
            revisionService.createSnapshot(current, username);

            WikiPage updated = wikiService.updatePage(id, mapper.toDomain(request));

            return mapper.toResponse(updated);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        wikiService.deletePage(id);
    }
    @PostMapping
    public ResponseEntity<WikiPageResponse> createPage(@Valid @RequestBody CreateWikiPageRequest request) {
        WikiPage created = wikiService.createPage(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }
    @GetMapping("/{id}")
    public WikiPageResponse getPageById(@PathVariable String id) {
        return mapper.toResponse(wikiService.getPageById(id));
    }
    @GetMapping
    public Page<WikiPageResponse> listPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        return wikiService.listPages(pageable).map(mapper::toResponse);
    }
    @GetMapping("/search")
    public Page<WikiPageResponse> searchByTitle(
            @RequestParam("title") String fragment,
            @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size
 ) {
        PageRequest pageable = PageRequest.of(page, size);
        return wikiService.searchByTitle(fragment, pageable).map(mapper::toResponse);
    }
}
