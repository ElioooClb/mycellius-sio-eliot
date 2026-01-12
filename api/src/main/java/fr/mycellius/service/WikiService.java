package fr.mycellius.service;

import fr.mycellius.repository.InMemoryWiki;
import fr.mycellius.domain.exception.PageNotFoundException;
import fr.mycellius.domain.WikiPage;
import java.util.List;

public class WikiService {
    private final InMemoryWiki repository;

    public WikiService(InMemoryWiki repository) {
        if (repository == null) {
            throw new IllegalArgumentException("repository obligatoire");
        }
        this.repository = repository;
    }
    /**
     * Crée une nouvelle page wiki.
     * Règles :
     * - id obligatoire, non vide (WikiPage se charge déjà de vérifier ça)
     * - title obligatoire, propre (géré par WikiPage)
     * - si une page existe déjà avec le même id -> IllegalArgumentException
     *
     * @param id identifiant unique de la page
     * @param title titre de la page
     * @param content contenu initial (peut être vide mais pas null)
     * @return la page créée
     */
    public WikiPage createPage(String id, String title, String content) {
        if (repository.existsById(id)) {
            throw new IllegalArgumentException("Une page existe déjà avec l'id " + id);
        }
        WikiPage page = new WikiPage(id, title, content);
        return repository.save(page);
    }
    /**
     * Retourne la page correspondant à l'id fourni.
     *
     * @param id identifiant de la page
     * @return la page trouvée
     * @throws PageNotFoundException si aucune page avec cet id
     */
    public WikiPage getPageById(String id) {
        return repository.getById(id);
    }
    /**
     * Recherche des pages dont le titre contient le fragment fourni (insensible à la
     casse).
     *
     * @param fragment morceau de texte à chercher dans le titre
     * @return liste (éventuellement vide) de pages correspondantes
     */
    public List<WikiPage> searchByTitle(String fragment) {
        return repository.searchByTitleContaining(fragment);
    }
    /**
     * Retourne toutes les pages existantes.
     */
    public List<WikiPage> listAllPages() {
        return repository.findAll();
    }
}