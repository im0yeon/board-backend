package com.imooyoni.board.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_INPUT(HttpStatus.BAD_REQUEST,"C400", "잘못된 입력입니다"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C401","인증이 필요합니다"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "C403","접근 권한이 없습니다"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "C404","%s 정보를 찾을 수 없습니다"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C405","지원하지 않는 요청 방식입니다"),
	INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "C4001","검색 기간이 올바르지 않습니다. 시작일(%s)이 종료일(%s)보다 늦습니다"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C4002","%s 값의 형식이 올바르지 않습니다: %s"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C500","서버 오류가 발생했습니다");

	private final HttpStatus status;
	private final String code;
	private final String message;

	// 도메인 전용 코드가 필요할 때
	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	// args 있으면 메시지 포맷 치환
	public String formatMessage(Object... args) {
		if (args == null || args.length == 0) {
			return message;
		}
		return String.format(message, args);
	}
}
