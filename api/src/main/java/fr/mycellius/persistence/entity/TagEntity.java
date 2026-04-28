package fr.mycellius.persistence.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "tag", uniqueConstraints = @UniqueConstraint(name = "uk_tag_value", columnNames = "value"))
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value", nullable = false, length = 30)
    private String value;
    public TagEntity() {}
    public TagEntity(String value) {
        this.value = value;
    }
    public Long getId() { return id; }
    public String getValue() { return value; }
}