package fr.mycellius.repository;
import fr.mycellius.domain.WikiPage;
import fr.mycellius.domain.exception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class InMemoryWiki implements WikiRepository {
    private final Map<String, WikiPage> store = new ConcurrentHashMap<>();
    @Override
    public WikiPage save(WikiPage page) {
        store.put(page.getId(), page);
        return page;
    }
    @Override
    public void deleteById(String id) {
        WikiPage removed = store.remove(id);
        if (removed == null) {
            throw new PageNotFoundException(id);
        }
    }
    @Override
    public WikiPage getById(String id) {
        return store.get(id);
    }
    @Override
    public Page<WikiPage> findAll(Pageable pageable) {
        List<WikiPage> all = new ArrayList<>(store.values());
        all.sort(Comparator.comparing(WikiPage::getId)); // simple et stable
        return toPage(all, pageable);
    }
    @Override
    public Page<WikiPage> searchByTitle(String title, Pageable pageable) {
        String needle = title == null ? "" : title.toLowerCase();
        List<WikiPage> filtered = store.values().stream()
                .filter(p -> p.getTitle() != null && p.getTitle().toLowerCase().contains(needle))
                .sorted(Comparator.comparing(WikiPage::getId))
                .toList();
        return toPage(filtered, pageable);
    }
    private Page<WikiPage> toPage(List<WikiPage> source, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), source.size());
        List<WikiPage> content = (start >= source.size()) ? List.of() : source.subList(start, end);
        return new PageImpl<>(content, pageable, source.size());
    }
}