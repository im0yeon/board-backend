package com.imooyoni.board.service.article;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.data.domain.article.ArticleDetailElements;
import com.imooyoni.board.data.domain.article.ArticleElements;
import com.imooyoni.board.data.domain.article.ArticleSearchCommand;

public interface ArticleService {

	PageResponse<ArticleElements> search(ArticleSearchCommand command, PageCommand page);

	ArticleDetailElements get(Long id);

	ArticleDetailElements write(String title
			, String content
			, String writer
			, Boolean isNotice
			, LocalDate createDate
			, MultipartFile file);

	ArticleDetailElements modify(
			Long id
			, String title
			, String content
			, String writer
			, Boolean isNotice
			, Boolean isRemoveFile
			, MultipartFile file);


}
