package com.imooyoni.board.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooyoni.board.common.utils.paging.PageCommand;
import com.imooyoni.board.common.utils.paging.PageResponse;
import com.imooyoni.board.config.exception.ErrorCode;

import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

	private static final String SUCCESS_CODE = "OK";
	private static final int SUCCESS_STATUS = 200;


	private final int status;
	private final String code;
	private final T data;
	private final String error;

	private BaseResponse(int status, String code, T data, String error) {
		this.status = status;
		this.code = code;
		this.data = data;
		this.error = error;
	}

	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>(SUCCESS_STATUS, SUCCESS_CODE, data, null);
	}

	public static BaseResponse<Void> success() {
		return new BaseResponse<>(SUCCESS_STATUS, SUCCESS_CODE, null, null);
	}

	public static <E> BaseResponse<PageResponse<E>> success(
			List<E> content, PageCommand command, long totalElements) {
		return success(PageResponse.of(content, command, totalElements));
	}

	public static BaseResponse<Object> error(BaseException e) {
		ErrorCode errorCode = e.getErrorCode();
		return new BaseResponse<>(
				errorCode.getStatus().value(),
				errorCode.getCode(),
				e.getData(),
				e.getMessage());
	}

	public static BaseResponse<Object> error(ErrorCode errorCode, Object... args) {
		return new BaseResponse<>(
				errorCode.getStatus().value(),
				errorCode.getCode(),
				null,
				errorCode.formatMessage(args));
	}
}
