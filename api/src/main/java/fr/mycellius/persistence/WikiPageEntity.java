package fr.mycellius.persistence;
import jakarta.persistence.*;

@Entity
@Table(name = "wiki_page")
public class WikiPageEntity {
    @Id
    private String id;
    @Column(nullable = false, length = 255)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;
    protected WikiPageEntity() {
        // constructeur sans argument pour JPA
    }
    public WikiPageEntity(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
// getters / setters classiques
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}