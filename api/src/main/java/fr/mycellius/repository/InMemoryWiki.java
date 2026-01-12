package fr.mycellius.repository;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.exception.PageNotFoundException;
import java.util.*;

public class InMemoryWiki {
    private final Map<String, WikiPage> pages = new HashMap<>();
    public WikiPage save(WikiPage page) {
        if (page == null) {
            throw new IllegalArgumentException("page null interdite");
        }
        pages.put(page.getId(), page);
        return page;
    }
    public boolean existsById(String id) {
        return pages.containsKey(id);
    }
    public WikiPage getById(String id) {
        WikiPage page = pages.get(id);
        if (page == null) {
            throw new PageNotFoundException(id);
        }
        return page;
    }
    public List<WikiPage> searchByTitleContaining(String fragment) {
        if (fragment == null) {
            throw new IllegalArgumentException("fragment de recherche null");
        }
        String needle = fragment.trim().toLowerCase(Locale.ROOT);
        List<WikiPage> result = new ArrayList<>();
        for (WikiPage page : pages.values()) {
            String title = page.getTitle();
            if (title != null && title.toLowerCase(Locale.ROOT).contains(needle)) {
                result.add(page);
            }
        }
        return result;
    }
    public List<WikiPage> findAll() {
        return new ArrayList<>(pages.values());
    }
}