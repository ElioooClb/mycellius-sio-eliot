package fr.mycellius.service;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.repository.InMemoryWiki;
import fr.mycellius.domain.exception.PageNotFoundException;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class WikiServiceTest {
    @Test
    void createPage_nominal_cree_une_page() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        WikiPage page = service.createPage("PAGE-001", "Installation Docker", "contenu");
        assertEquals("PAGE-001", page.getId());
        assertEquals("Installation Docker", page.getTitle());
        assertEquals(1, repo.findAll().size());
    }
    @Test
    void createPage_refuse_doublon_id() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        service.createPage("PAGE-001", "Titre 1", "contenu");
        assertThrows(IllegalArgumentException.class, () ->
                service.createPage("PAGE-001", "Titre 2", "autre contenu")
        );
    }
    @Test
    void getPageById_page_existante_ok() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        service.createPage("PAGE-001", "Titre", "contenu");
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
        List<WikiPage> result = service.searchByTitle("docker");
        assertTrue(result.isEmpty());
    }
    @Test
    void searchByTitle_trouve_pages_correspondantes() {
        InMemoryWiki repo = new InMemoryWiki();
        WikiService service = new WikiService(repo);
        service.createPage("PAGE-001", "Installation Docker", "...");
        service.createPage("PAGE-002", "Docker avancé", "...");
        List<WikiPage> result = service.searchByTitle("docker");
        assertEquals(2, result.size());
    }
}
