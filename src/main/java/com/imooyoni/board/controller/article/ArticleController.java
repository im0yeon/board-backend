package com.imooyoni.board.controller.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imooyoni.board.common.BaseResponse;
import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.service.article.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping
	public BaseResponse<PageResponse<ArticleElements>> search(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String writer,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		return BaseResponse.success(
				articleService.search(ArticleSearchCommand.of(keyword, writer), PageCommand.of(page, size)));
	}

	@GetMapping("/{id}")
	public BaseResponse<Article> get(@PathVariable Long id) {
		return BaseResponse.success(articleService.get(id));
	}

}
