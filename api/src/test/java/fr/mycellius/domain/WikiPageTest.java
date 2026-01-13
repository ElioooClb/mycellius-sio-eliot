package fr.mycellius.domain;
import fr.mycellius.domain.WikiPage;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class WikiPageTest {
    @Test
    void testTitreNullInterdit() {
        assertThrows(IllegalArgumentException.class, () ->
                new WikiPage("PAGE-001", null, "contenu")
        );

    }
    @Test
    void testTitreVideInterdit() {
        assertThrows(IllegalArgumentException.class, () ->
                new WikiPage("PAGE-001", null, "contenu")
        );

    }
    @Test
    void testTitreNettoyeAvecTrim() {
        WikiPage page = new WikiPage("PAGE-001", " SSH ", "contenu");
        assertEquals("SSH", page.getTitle());

    }
}