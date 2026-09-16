package com.imooyoni.board.data.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "board", comment = "게시글")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, comment = "게시글 ID")
    private Long id;

    @Column(name = "title", nullable = false, length = 200, comment = "제목")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text", comment = "내용")
    private String content;

    @Column(name = "writer", nullable = false, length = 50, comment = "작성자")
    private String writer;

    @Column(name = "created_at", nullable = false, comment = "등록일")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, comment = "수정일")
    private LocalDateTime updatedAt;

    private BoardEntity(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static BoardEntity write(String title, String content, String writer) {
        return new BoardEntity(title, content, writer);
    }

    public void modify(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
