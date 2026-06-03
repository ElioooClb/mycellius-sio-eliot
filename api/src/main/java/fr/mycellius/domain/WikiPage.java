package fr.mycellius.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WikiPage {

    private String id;
    private String title;
    private String content;
    private boolean confidential;

    // Tags métier
    private List<Tag> tags = new ArrayList<>();

    // Date de création utile pour le tri et le retour API
    private Instant createdAt;

    public WikiPage() {
    }

    public WikiPage(String id, String title, String content) {
        this(id, title, content, List.of(), null, false);
    }

    public WikiPage(String id, String title, String content, List<Tag> tags) {
        this(id, title, content, tags, null, false);
    }

    public WikiPage(
            String id,
            String title,
            String content,
            List<Tag> tags,
            Instant createdAt) {
        this(id, title, content, tags, createdAt, false);
    }

    public WikiPage(
            String id,
            String title,
            String content,
            List<Tag> tags,
            Instant createdAt,
            boolean confidential) {
        this.id = id;
        this.title = cleanTitle(title);
        this.content = content;
        this.tags = (tags == null) ? new ArrayList<>() : new ArrayList<>(tags);
        this.createdAt = createdAt;
        this.confidential = confidential;
    }

    // Source de vérité : règles métier sur le titre
    private static String cleanTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }

        return title.trim();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    // MapStruct passe ici : on protège aussi ce chemin-là
    public void setTitle(String title) {
        this.title = cleanTitle(title);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public void setTags(List<Tag> tags) {
        this.tags = (tags == null) ? new ArrayList<>() : new ArrayList<>(tags);
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}