package fr.mycellius.service;

import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.exception.PageNotFoundException;
import fr.mycellius.repository.WikiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WikiService {

    private final WikiRepository repository;

    public WikiService(WikiRepository repository) {
        this.repository = repository;
    }

    public void deletePage(String id) {
        repository.deleteById(id);
    }

    public WikiPage createPage(WikiPage page) {
        if (page == null) {
            throw new IllegalArgumentException("La page est obligatoire");
        }

        WikiPage existing = repository.getById(page.getId());
        if (existing != null) {
            throw new IllegalArgumentException("Une page avec l'id " + page.getId() + " existe déjà");
        }

        return repository.save(page);
    }

    public WikiPage getPageById(String id) {
        WikiPage page = repository.getById(id);
        if (page == null) {
            throw new PageNotFoundException(id);
        }
        return page;
    }

    public WikiPage updatePage(String id, WikiPage page) {
        if (page == null) {
            throw new IllegalArgumentException("La page est obligatoire");
        }

        WikiPage existing = repository.getById(id);
        if (existing == null) {
            throw new PageNotFoundException(id);
        }

        WikiPage updated = new WikiPage(
                id,
                page.getTitle(),
                page.getContent(),
                page.getTags()
        );

        return repository.save(updated);
    }

    public Page<WikiPage> listPages(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<WikiPage> searchByTitle(String fragment, Pageable pageable) {
        return repository.searchByTitle(fragment, pageable);
    }
}