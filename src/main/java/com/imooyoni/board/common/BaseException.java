package com.imooyoni.board.common;

import com.imooyoni.board.config.exception.ErrorCode;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

	private final ErrorCode errorCode;
	private final Object data;
	private final transient Object[] args;

	private BaseException(ErrorCode errorCode, Object data, Object... args) {
		super(errorCode.formatMessage(args));
		this.errorCode = errorCode;
		this.data = data;
		this.args = args;
	}

	public static BaseException of(ErrorCode errorCode) {
		return new BaseException(errorCode, null);
	}

	public static BaseException of(ErrorCode errorCode, Object data, Object... args) {
		return new BaseException(errorCode, data, args);
	}
}
