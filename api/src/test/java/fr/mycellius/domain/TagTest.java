package fr.mycellius.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TagTest {
    @Test
    void testTagNormaliseEnMinusculesEtTrim() {
        Tag tag = new Tag(" Docker ");
        assertEquals("docker", tag.getValue());
    }
    @Test
    void testTagVideInterdit() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tag(" ")
        );
    }
    @Test
    void testTagNullInterdit() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tag(null)
        );
    }
}