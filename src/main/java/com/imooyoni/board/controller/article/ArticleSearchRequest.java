package com.imooyoni.board.controller.article;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.imooyoni.board.common.utils.paging.PagingRequest;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleSearchRequest extends PagingRequest {

	private String keyword;
	private String writer;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;

	public ArticleSearchCommand toSearchCommand() {
		return ArticleSearchCommand.of(keyword, writer, startDate, endDate);
	}

	@Override
	protected void addSearchParams(List<String> params) {
		add(params, "keyword", keyword);
		add(params, "writer", writer);
		add(params, "startDate", startDate);
		add(params, "endDate", endDate);
	}
}
