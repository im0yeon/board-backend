package com.imooyoni.board.service.article;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.config.exception.ErrorCode;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleRepository;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	@Override
	public PageResponse<ArticleElements> search(ArticleSearchCommand command, PageCommand page) {
		long total = articleRepository.countBy(command);
		if (total == 0) {
			return PageResponse.empty(page);
		}

		List<ArticleElements> content = articleRepository.search(command, page);
		return PageResponse.of(content, page, total);
	}

	@Override
	public Article get(Long id) {
		return articleRepository.findById(id)
				.orElseThrow(() -> BaseException.of(ErrorCode.NOT_FOUND, null, "게시글"));
	}

	@Override
	@Transactional
	public Article write(String title, String content, String writer) {
		return articleRepository.save(Article.write(title, content, writer));
	}
}
