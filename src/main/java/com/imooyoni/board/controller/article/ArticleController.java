package com.imooyoni.board.controller.article;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.imooyoni.board.common.BaseResponse;
import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.service.article.ArticleService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping
	public BaseResponse<PageResponse<ArticleElements>> search(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String writer,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		return BaseResponse.success(
				articleService.search(
						ArticleSearchCommand.of(keyword, writer, startDate, endDate)
						, PageCommand.of(page, size)
				)
		);
	}

	@GetMapping("/{id}")
	public BaseResponse<Article> get(@PathVariable Long id) {
		return BaseResponse.success(
				articleService.get(id)
		);
	}
}
