package fr.mycellius.domain;
import org.junit.jupiter.api.Test;
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
                new WikiPage("PAGE-001", " ", "contenu")
        );
    }
    @Test
    void testTitreNettoyeAvecTrim() {
        WikiPage page = new WikiPage("PAGE-001", " SSH ", "contenu");
        assertEquals("SSH", page.getTitle());
    }
}