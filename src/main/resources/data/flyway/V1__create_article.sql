CREATE TABLE article (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    title      VARCHAR(200) NOT NULL,
    content    TEXT         NOT NULL,
    writer     VARCHAR(50)  NOT NULL,
    notice_yn  CHAR(1)      DEFAULT 'N' NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_article_writer ON article (writer);
