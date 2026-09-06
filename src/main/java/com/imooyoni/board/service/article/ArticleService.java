package com.imooyoni.board.service.article;

import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;

public interface ArticleService {

	PageResponse<ArticleElements> search(ArticleSearchCommand command, PageCommand page);

	Article get(Long id);

	Article write(String title, String content, String writer);
}
