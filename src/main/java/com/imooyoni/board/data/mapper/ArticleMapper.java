package com.imooyoni.board.data.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.imooyoni.board.data.domain.article.ArticleDetailElements;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;

@Mapper
public interface ArticleMapper {

	List<ArticleElements> search(
			@Param("cond") ArticleSearchCommand cond,
			@Param("offset") long offset,
			@Param("limit") int limit);

	long countBy(@Param("cond") ArticleSearchCommand cond);

	Optional<ArticleDetailElements> findDetailById(@Param("id") Long id);
}
