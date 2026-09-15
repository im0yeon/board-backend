package com.imooyoni.board.controller.article;

import com.imooyoni.board.data.domain.article.ArticleDetailElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.imooyoni.board.common.BaseResponse;
import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.service.article.ArticleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.List;

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
	public BaseResponse<ArticleDetailElements> get(@PathVariable Long id) {
		return BaseResponse.success(
				articleService.get(id)
		);
	}

	@PostMapping
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

	@PutMapping("/{id}")
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

	@DeleteMapping("/{id}")
	public BaseResponse<Void> delete(@PathVariable Long id) {
		articleService.delete(List.of(id));
		return BaseResponse.success();
	}

	@DeleteMapping
	public BaseResponse<Void> delete(@RequestParam List<Long> ids) {
		articleService.delete(ids);
		return BaseResponse.success();
	}
}
