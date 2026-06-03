package fr.mycellius.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@SuppressWarnings("unused")
@Table(name = "wiki_page")
public class WikiPageEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "confidential", nullable = false)
    private boolean confidential;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToMany
    @JoinTable(name = "wiki_page_tag", joinColumns = @JoinColumn(name = "page_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();

    public WikiPageEntity() {
        // constructeur sans argument pour JPA
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }
}