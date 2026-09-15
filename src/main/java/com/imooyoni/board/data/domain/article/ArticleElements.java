package com.imooyoni.board.data.domain.article;

import java.time.LocalDateTime;

public record ArticleElements(
		long id,
		String title,
		String writer,
		boolean isNotice,
		LocalDateTime createdAt
) {
}
