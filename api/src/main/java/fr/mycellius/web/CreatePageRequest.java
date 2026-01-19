package fr.mycellius.web;
import fr.mycellius.domain.Tag;
import java.util.List;

public record CreatePageRequest(
        String id,
        String title,
        String content,
        List<Tag> tags
) {}
