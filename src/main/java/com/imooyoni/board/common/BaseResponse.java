package com.imooyoni.board.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooyoni.board.config.exception.ErrorCode;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

	private static final String SUCCESS_CODE = "OK";

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
		return new BaseResponse<>(200, SUCCESS_CODE, data, null);
	}

	public static BaseResponse<Void> success() {
		return new BaseResponse<>(200, SUCCESS_CODE, null, null);
	}

	public static <T> BaseResponse<T> error(BaseException e) {
		ErrorCode errorCode = e.getErrorCode();
		return new BaseResponse<>(
				errorCode.getStatus().value(),
				errorCode.getCode(),
				castData(e.getData()),
				e.getMessage());
	}

	public static <T> BaseResponse<T> error(ErrorCode errorCode, Object... args) {
		return new BaseResponse<>(
				errorCode.getStatus().value(),
				errorCode.getCode(),
				null,
				errorCode.formatMessage(args));
	}

	@SuppressWarnings("unchecked")
	private static <T> T castData(Object data) {
		return (T) data;
	}
}
