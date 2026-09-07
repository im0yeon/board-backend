package com.imooyoni.board.common.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateUtils {

	private DateUtils() {
	}

	public static LocalDateTime startOfDay(LocalDate date) {
		return date == null ? null : date.atStartOfDay();
	}

	// 기간 조회의 종료 경계 - 당일을 포함하려면 다음 날 0시 미만으로 비교한다
	public static LocalDateTime startOfNextDay(LocalDate date) {
		return date == null ? null : date.plusDays(1).atStartOfDay();
	}

	public static boolean isReversed(LocalDate start, LocalDate end) {
		return start != null && end != null && start.isAfter(end);
	}
}
