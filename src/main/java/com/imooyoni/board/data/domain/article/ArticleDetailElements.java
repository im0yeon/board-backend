package com.imooyoni.board.data.domain.article;

import java.time.LocalDateTime;

public record ArticleDetailElements(
		long id,
		String title,
		String content,
		String writer,
		boolean isNotice,
		String fileUrl,
		LocalDateTime createdAt
) {

	public static ArticleDetailElements from(Article article) {
		return new ArticleDetailElements(
				article.getId()
				, article.getTitle()
				, article.getContent()
				, article.getWriter()
				, article.isNotice()
				, article.getFileUrl()
				, article.getCreatedAt()
		);
	}
}
