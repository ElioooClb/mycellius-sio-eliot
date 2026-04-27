CREATE TABLE IF NOT EXISTS wiki_page_revision (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 page_id VARCHAR(64) NOT NULL,
 title VARCHAR(255) NOT NULL,
 content TEXT NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 created_by VARCHAR(64) NOT NULL
);
CREATE INDEX idx_revision_page_id ON wiki_page_revision(page_id);