package com.imooyoni.board.controller.article;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
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
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			Model model) {

		model.addAttribute("articles", articleService.search(
				ArticleSearchCommand.of(keyword, writer, startDate, endDate), PageCommand.of(page, size)));
		model.addAttribute("keyword", keyword);
		model.addAttribute("writer", writer);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "article/list";
	}

	@GetMapping("/articles/{id}")
	public String detail(
			@PathVariable Long id,
			Model model
	) {
		model.addAttribute("article", articleService.get(id));
		return "article/detail";
	}

	@GetMapping("/articles/new")
	public String write(
			Model model
	) {
		return "article/form";
	}

	@GetMapping("/articles/{id}/edit")
	public String update(
			@PathVariable Long id,
			Model model
	) {
		model.addAttribute("article", articleService.get(id));
		model.addAttribute("mode", "edit");
		return "article/form";
	}
}
