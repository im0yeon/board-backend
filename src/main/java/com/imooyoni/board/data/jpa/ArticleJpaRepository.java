package com.imooyoni.board.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooyoni.board.data.domain.article.Article;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
}
