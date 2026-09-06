package com.imooyoni.board.data.domain.article;

import com.imooyoni.board.common.utils.lang.StringUtils;

public record ArticleSearchCommand(String keyword, String writer) {

	public static ArticleSearchCommand of(String keyword, String writer) {
		return new ArticleSearchCommand(StringUtils.trimToNull(keyword), StringUtils.trimToNull(writer));
	}
}
