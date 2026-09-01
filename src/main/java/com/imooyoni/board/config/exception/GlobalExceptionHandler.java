package com.imooyoni.board.config.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.warn("업무 예외 발생: code={}, message={}, data={}", errorCode.getCode(), e.getMessage(), e.getData());

		return ResponseEntity.status(errorCode.getStatus())
				.body(BaseResponse.error(e));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException e) {
		String detail = e.getBindingResult().getFieldErrors().stream()
				.map(field -> field.getField() + ": " + field.getDefaultMessage())
				.collect(Collectors.joining(", "));
		log.warn("입력값 검증 실패: {}", detail);

		return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus())
				.body(BaseResponse.error(BaseException.of(ErrorCode.INVALID_INPUT, detail)));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<BaseResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		log.warn("지원하지 않는 요청 방식: method={}", e.getMethod());

		return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
				.body(BaseResponse.error(ErrorCode.METHOD_NOT_ALLOWED));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<BaseResponse<Object>> handleNoResource(NoResourceFoundException e) {
		log.warn("존재하지 않는 경로 요청: path={}", e.getResourcePath());

		return ResponseEntity.status(ErrorCode.NOT_FOUND.getStatus())
				.body(BaseResponse.error(ErrorCode.NOT_FOUND, "요청 경로"));
	}

	// 내부 오류 상세는 응답에 노출하지 않음
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<Object>> handleException(Exception e) {
		log.error("처리되지 않은 예외 발생", e);

		return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}
