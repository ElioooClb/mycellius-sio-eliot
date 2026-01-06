package fr.mycellius.domain;

public class WikiPage {
    private final String id;
    private String title;
    private String content;
    public WikiPage(String id, String title, String content) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id obligatoire");
        }
        this.id = id.trim();
        setTitle(title);
        this.content = (content == null) ? "" : content;
    }
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("titre obligatoire");
        }
        this.title = title.trim();
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}