CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_user_username (username)
    ) ENGINE=InnoDB;
