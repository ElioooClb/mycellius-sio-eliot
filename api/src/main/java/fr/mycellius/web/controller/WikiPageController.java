package fr.mycellius.web.controller;

import fr.mycellius.domain.WikiPage;
import fr.mycellius.service.WikiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public WikiPageController(WikiService wikiService, WikiPageDtoMapper mapper) {
        this.wikiService = wikiService;
        this.mapper = mapper;
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
