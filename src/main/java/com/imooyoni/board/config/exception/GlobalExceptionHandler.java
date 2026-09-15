package com.imooyoni.board.config.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.BaseResponse;
import com.imooyoni.board.common.utils.io.FileUtils;

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

	// 날짜·숫자 등 파라미터 타입 변환 실패
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<BaseResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		log.warn("파라미터 형식 오류: name={}, value={}", e.getName(), e.getValue());

		return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getStatus())
				.body(BaseResponse.error(ErrorCode.INVALID_PARAMETER, e.getName(), e.getValue()));
	}

	// 서블릿 한도 초과 - 컨트롤러 진입 전에 발생해 FileUtils 검사가 닿지 않는다
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<BaseResponse<Object>> handleUploadSize(MaxUploadSizeExceededException e) {
		log.warn("업로드 크기 초과: 서블릿 한도={}bytes", e.getMaxUploadSize());

		String limit = FileUtils.formatSize(FileUtils.MAX_ATTACHMENT_SIZE);
		return ResponseEntity.status(ErrorCode.FILE_TOO_LARGE.getStatus())
				.body(BaseResponse.error(ErrorCode.FILE_TOO_LARGE, limit));
	}

	// 내부 오류 상세는 응답에 노출하지 않음
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<Object>> handleException(Exception e) {
		log.error("처리되지 않은 예외 발생", e);

		return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}
