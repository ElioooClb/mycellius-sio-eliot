package fr.mycellius.persistence.revision;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "wiki_page_revision")
public class WikiPageRevisionEntity {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @Column(name = "page_id", nullable = false, length = 64)
 private String pageId;
 @Column(nullable = false, length = 255)
 private String title;
 @Lob
 @Column(nullable = false)
 private String content;
 @Column(name = "created_at", nullable = false)
 private Instant createdAt;
 @Column(name = "created_by", nullable = false, length = 64)
 private String createdBy;
 protected WikiPageRevisionEntity() {}
 public WikiPageRevisionEntity(String pageId, String title, String content, String
createdBy) {
 this.pageId = pageId;
 this.title = title;
 this.content = content;
 this.createdBy = createdBy;
 this.createdAt = Instant.now();
 }
 public Long getId() { return id; }
 public String getPageId() { return pageId; }
 public String getTitle() { return title; }
 public String getContent() { return content; }
 public Instant getCreatedAt() { return createdAt; }
 public String getCreatedBy() { return createdBy; }
}