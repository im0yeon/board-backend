package com.imooyoni.board.service.article;

import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;
import com.imooyoni.board.data.domain.article.ArticleElements;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface ArticleService {

	PageResponse<ArticleElements> search(ArticleSearchCommand command, PageCommand page);

	Article get(Long id);

	Article write(String title
			, String content
			, String writer
			, Boolean isNotice
			, LocalDate createDate
			, MultipartFile file);

	Article modify(
			Long id
			, String title
			, String content
			, String writer
			, Boolean isNotice
			, Boolean isRemoveFile
			, MultipartFile file);
}
