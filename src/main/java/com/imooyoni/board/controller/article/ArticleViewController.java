package com.imooyoni.board.controller.article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.service.article.ArticleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArticleViewController {

	private final ArticleService articleService;

	@GetMapping("/articles")
	public String list(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String writer,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			Model model) {

		model.addAttribute("articles",
				articleService.search(ArticleSearchCommand.of(keyword, writer), PageCommand.of(page, size)));
		model.addAttribute("keyword", keyword);
		model.addAttribute("writer", writer);

		return "article/list";
	}

	@GetMapping("/articles/detail/{id}")
	public String detail(
			@PathVariable Long id,
			Model model
	) {
		model.addAttribute("article", articleService.get(id));
		return "article/detail";
	}

	@GetMapping("/articles/write")
	public String write(
			Model model
	) {
		return "article/write";
	}
}
