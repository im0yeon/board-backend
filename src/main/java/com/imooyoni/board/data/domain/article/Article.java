package com.imooyoni.board.data.domain.article;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import org.hibernate.type.YesNoConverter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(nullable = false, columnDefinition = "text")
	private String content;

	@Column(nullable = false, length = 50)
	private String writer;

	@Column(name = "notice_yn", nullable = false, length = 1)
	@Convert(converter = YesNoConverter.class)
	private boolean isNotice;

	@Column(name = "file_url", nullable = true, length = 200)
	private String fileUrl;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	private Article(String title, String content, String writer, boolean isNotice, LocalDateTime createdAt) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.isNotice = isNotice;
		this.createdAt = createdAt;
	}

	public static Article write(String title, String content, String writer, boolean isNotice, LocalDateTime createdAt) {
		return new Article(title, content, writer, isNotice, createdAt);
	}

	public void modify(String title, String content, String writer, boolean isNotice) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.isNotice = isNotice;
	}

	public void attachFile(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void detachFile() {
		this.fileUrl = null;
	}

	@PrePersist
	void onCreate() {
		if (this.createdAt == null) {
			this.createdAt = LocalDateTime.now();
		}
		this.updatedAt = this.createdAt;
	}

	@PreUpdate
	void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
