package fr.mycellius.service;
import fr.mycellius.service.RevisionService;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.persistence.revision.WikiPageRevisionEntity;
import fr.mycellius.persistence.revision.WikiPageRevisionJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevisionService {

    private final WikiPageRevisionJpaRepository repo;

    public RevisionService(WikiPageRevisionJpaRepository repo) {
        this.repo = repo;
    }

    public void createSnapshot(WikiPage currentPage, String actorUsername) {
        repo.save(new WikiPageRevisionEntity(
                currentPage.getId(),
                currentPage.getTitle(),
                currentPage.getContent(),
                actorUsername
        ));
    }

    public List<WikiPageRevisionEntity> list(String pageId) {
        return repo.findByPageIdOrderByCreatedAtDesc(pageId);
    }
}