package com.imooyoni.board.service.article;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.utils.io.FileUtils;
import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.config.exception.ErrorCode;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleDetailElements;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.data.domain.article.ArticleRepository;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.service.file.FileStorage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

	private static final String MENU = "article";

	private final ArticleRepository articleRepository;
	private final FileStorage fileStorage;

	@Override
	public PageResponse<ArticleElements> search(ArticleSearchCommand command, PageCommand page) {
		long total = articleRepository.countBy(command);
		if (total == 0) {
			return PageResponse.empty(page);
		}

		PageCommand paged = page.clampTo(total);
		List<ArticleElements> content = articleRepository.search(command, paged);
		return PageResponse.of(content, paged, total);
	}

	@Override
	public ArticleDetailElements get(Long id) {
		return articleRepository.findDetailById(id)
				.orElseThrow(() -> BaseException.of(ErrorCode.NOT_FOUND, null, "게시글"));
	}

	@Override
	@Transactional
	public ArticleDetailElements write(
			String title
			, String content
			, String writer
			, Boolean isNotice
			, LocalDate createDate
			, MultipartFile file
	) {
		boolean hasFile = !FileUtils.isEmpty(file);
		if (hasFile) {
			FileUtils.verify(file);
		}

		Article article = articleRepository.save(
				Article.write(
					title
					, content
					, writer
					, Boolean.TRUE.equals(isNotice)
					, createDate == null ? null : createDate.atStartOfDay()
				)
		);

		if (hasFile) {
			article.attachFile(fileStorage.store(file, MENU, article.getId()));
			articleRepository.save(article);
		}

		return ArticleDetailElements.from(article);
	}

	@Override
	@Transactional
	public ArticleDetailElements modify(
			Long id
			, String title
			, String content
			, String writer
			, Boolean isNotice
			, Boolean isRemoveFile
			, MultipartFile file
	) {
		boolean hasFile = !FileUtils.isEmpty(file);
		if (hasFile) {
			FileUtils.verify(file);
		}

		Article article = getEntity(id);
		article.modify(title, content, writer, Boolean.TRUE.equals(isNotice));

		if (hasFile || Boolean.TRUE.equals(isRemoveFile)) {
			fileStorage.delete(article.getFileUrl());
			article.detachFile();
		}

		if (hasFile) {
			article.attachFile(fileStorage.store(file, MENU, article.getId()));
		}

		return ArticleDetailElements.from(articleRepository.save(article));
	}

	@Override
	@Transactional
	public void delete(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			throw BaseException.of(ErrorCode.INVALID_INPUT, "삭제할 게시글이 지정되지 않았습니다");
		}

		List<Article> articles = articleRepository.findAllById(ids);
		if (articles.size() != ids.size()) {
			throw BaseException.of(ErrorCode.NOT_FOUND, null, "게시글");
		}

		for (Article article : articles) {
			fileStorage.delete(article.getFileUrl());
			articleRepository.deleteById(article.getId());
		}
	}

	// 쓰기 경로는 영속 상태 엔티티가 필요하다
	private Article getEntity(Long id) {
		return articleRepository.findById(id)
				.orElseThrow(() -> BaseException.of(ErrorCode.NOT_FOUND, null, "게시글"));
	}
}
