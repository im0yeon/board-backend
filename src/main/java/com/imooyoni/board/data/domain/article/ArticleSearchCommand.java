package com.imooyoni.board.data.domain.article;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.utils.lang.StringUtils;
import com.imooyoni.board.common.utils.time.DateUtils;
import com.imooyoni.board.config.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ArticleSearchCommand(
		String keyword
		, String writer
		, LocalDate startDate
		, LocalDate endDate
) {

	public ArticleSearchCommand {
		if (DateUtils.isReversed(startDate, endDate)) {
			throw BaseException.of(ErrorCode.INVALID_DATE_RANGE, null, startDate, endDate);
		}
	}

	public static ArticleSearchCommand of(
			String keyword
			, String writer
			, LocalDate startDate
			, LocalDate endDate
	) {
		return new ArticleSearchCommand(
				StringUtils.trimToNull(keyword)
				, StringUtils.trimToNull(writer)
				, startDate
				, endDate
		);
	}

	public LocalDateTime startDateTime() {
		return DateUtils.startOfDay(startDate);
	}

	public LocalDateTime endDateTime() {
		return DateUtils.startOfNextDay(endDate);
	}
}
