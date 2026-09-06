package com.imooyoni.board.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;

@Mapper
public interface ArticleMapper {

	List<ArticleElements> search(
			@Param("cond") ArticleSearchCommand cond,
			@Param("offset") long offset,
			@Param("limit") int limit);

	long countBy(@Param("cond") ArticleSearchCommand cond);
}
