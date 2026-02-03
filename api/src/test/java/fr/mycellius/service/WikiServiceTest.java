package fr.mycellius.service;
import fr.mycellius.domain.Tag;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.exception.PageNotFoundException;
import fr.mycellius.repository.InMemoryWiki;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class WikiServiceTest {
    // Petite méthode utilitaire pour fabriquer une Liste de tags de test
    private List<Tag> tagsDocker() {
        return List.of(new Tag("Docker"));
    }
    @Test
    void createPage_nominal_cree_une_page() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        WikiPage page = service.createPage(new WikiPage("PAGE-001", "Installation Docker", "contenu",
                tagsDocker()));
        assertEquals("PAGE-001", page.getId());
        assertEquals("Installation Docker", page.getTitle());
        // repo.findAll() attend maintenant un Pageable
        assertEquals(1, repo.findAll(PageRequest.of(0, 10)).getContent().size());
    }
    @Test
    void createPage_refuse_doublon_id() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        service.createPage(new WikiPage("PAGE-001", "Titre 1", "contenu", tagsDocker()));
        assertThrows(IllegalArgumentException.class, () ->
                service.createPage(new WikiPage("PAGE-001", "Titre 2", "autre contenu", tagsDocker()))
        );
    }
    @Test
    void getPageById_page_existante_ok() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        service.createPage(new WikiPage("PAGE-001", "Titre", "contenu", tagsDocker()));
        WikiPage page = service.getPageById("PAGE-001");
        assertEquals("PAGE-001", page.getId());
    }
    @Test
    void getPageById_page_inexistante_declenche_exception() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        assertThrows(PageNotFoundException.class, () ->
                service.getPageById("PAGE-999")
        );
    }
    @Test
    void searchByTitle_retourne_liste_vide_si_rien() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        // searchByTitle attend maintenant (fragment, pageable)
        List<WikiPage> result = service.searchByTitle("docker", PageRequest.of(0, 10)).getContent();
        assertTrue(result.isEmpty());
    }
    @Test
    void searchByTitle_trouve_pages_correspondantes() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        service.createPage(new WikiPage("PAGE-001", "Installation Docker", "...", tagsDocker()));
        service.createPage(new WikiPage("PAGE-002", "Docker avancé", "...", tagsDocker()));
        // searchByTitle attend maintenant (fragment, pageable)
        List<WikiPage> result = service.searchByTitle("docker", PageRequest.of(0, 10)).getContent();
        assertEquals(2, result.size());
    }
}