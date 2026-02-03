CREATE TABLE IF NOT EXISTS tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    value VARCHAR(30) NOT NULL UNIQUE
    );
CREATE TABLE IF NOT EXISTS wiki_page_tag (
    page_id VARCHAR(64) NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (page_id, tag_id),
    CONSTRAINT fk_wpt_page FOREIGN KEY (page_id) REFERENCES wiki_page(id),
    CONSTRAINT fk_wpt_tag FOREIGN KEY (tag_id) REFERENCES tag(id)
    );
