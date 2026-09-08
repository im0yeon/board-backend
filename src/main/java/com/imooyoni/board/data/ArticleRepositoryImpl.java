package com.imooyoni.board.data;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleDetailElements;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.data.domain.article.ArticleRepository;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.jpa.ArticleJpaRepository;
import com.imooyoni.board.data.mapper.ArticleMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

	private final ArticleJpaRepository jpaRepository;
	private final ArticleMapper articleMapper;

	@Override
	public Article save(Article article) {
		return jpaRepository.save(article);
	}

	@Override
	public Optional<Article> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public Optional<ArticleDetailElements> findDetailById(Long id) {
		return articleMapper.findDetailById(id);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public List<ArticleElements> search(ArticleSearchCommand command, PageCommand page) {
		return articleMapper.search(command, page.offset(), page.limit());
	}

	@Override
	public long countBy(ArticleSearchCommand command) {
		return articleMapper.countBy(command);
	}


}
