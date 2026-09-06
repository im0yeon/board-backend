package com.imooyoni.board.data.domain.article;

import java.util.List;
import java.util.Optional;

import com.imooyoni.board.common.utils.paging.PageCommand;

public interface ArticleRepository {

	Article save(Article article);

	Optional<Article> findById(Long id);

	void deleteById(Long id);

	List<ArticleElements> search(ArticleSearchCommand command, PageCommand page);

	long countBy(ArticleSearchCommand command);
}
