CREATE TABLE board (
    id         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '게시글 ID',
    title      VARCHAR(200) NOT NULL                COMMENT '제목',
    content    TEXT         NOT NULL                COMMENT '내용',
    writer     VARCHAR(50)  NOT NULL                COMMENT '작성자',
    created_at DATETIME     NOT NULL                COMMENT '등록일',
    updated_at DATETIME     NOT NULL                COMMENT '수정일',
    PRIMARY KEY (id)
) COMMENT='게시글';

CREATE INDEX idx_board_created_at ON board (created_at);
