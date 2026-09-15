package com.imooyoni.board.controller.article;

import com.imooyoni.board.data.domain.article.ArticleDetailElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.imooyoni.board.common.BaseResponse;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.service.article.ArticleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	// ==================== View ====================

	@GetMapping("/articles")
	public String list(
			@ModelAttribute("search") ArticleSearchRequest search,
			Model model) {

		model.addAttribute("articles",
				articleService.search(search.toSearchCommand(), search.toPageCommand()));

		return "article/list";
	}

	@GetMapping("/articles/{id}")
	public String detail(
			@PathVariable Long id,
			@ModelAttribute("search") ArticleSearchRequest search,
			Model model
	) {
		model.addAttribute("article", articleService.get(id));
		return "article/detail";
	}

	@GetMapping("/articles/new")
	public String writeForm(
			@ModelAttribute("search") ArticleSearchRequest search,
			Model model
	) {
		return "article/form";
	}

	@GetMapping("/articles/{id}/edit")
	public String editForm(
			@PathVariable Long id,
			@ModelAttribute("search") ArticleSearchRequest search,
			Model model
	) {
		model.addAttribute("article", articleService.get(id));
		model.addAttribute("mode", "edit");
		return "article/form";
	}

	// ==================== API ====================

	@GetMapping("/api/articles")
	@ResponseBody
	public BaseResponse<PageResponse<ArticleElements>> search(@ModelAttribute ArticleSearchRequest request) {
		return BaseResponse.success(
				articleService.search(request.toSearchCommand(), request.toPageCommand())
		);
	}

	@GetMapping("/api/articles/{id}")
	@ResponseBody
	public BaseResponse<ArticleDetailElements> get(@PathVariable Long id) {
		return BaseResponse.success(
				articleService.get(id)
		);
	}

	@PostMapping("/api/articles")
	@ResponseBody
	public BaseResponse<ArticleDetailElements> write(
			@RequestParam String title,
			@RequestParam String content,
			@RequestParam(defaultValue = "false") Boolean isNotice,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createDate,
			@RequestParam String writer,
			@RequestParam(value = "file", required = false) MultipartFile file
	) {
		return BaseResponse.success(
				articleService.write(title, content, writer, isNotice, createDate, file)
		);
	}

	@PutMapping("/api/articles/{id}")
	@ResponseBody
	public BaseResponse<ArticleDetailElements> modify(
			@PathVariable Long id,
			@RequestParam String title,
			@RequestParam String content,
			@RequestParam(defaultValue = "false") Boolean isNotice,
			@RequestParam String writer,
			@RequestParam(defaultValue = "false") Boolean isRemoveFile,
			@RequestParam(value = "file", required = false) MultipartFile file
	) {
		return BaseResponse.success(
				articleService.modify(id, title, content, writer, isNotice, isRemoveFile, file)
		);
	}

	@DeleteMapping("/api/articles/{id}")
	@ResponseBody
	public BaseResponse<Void> delete(@PathVariable Long id) {
		articleService.delete(List.of(id));
		return BaseResponse.success();
	}

	@DeleteMapping("/api/articles")
	@ResponseBody
	public BaseResponse<Void> delete(@RequestParam List<Long> ids) {
		articleService.delete(ids);
		return BaseResponse.success();
	}
}
