package com.imooyoni.board.common;

import com.imooyoni.board.config.exception.ErrorCode;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

	private final ErrorCode errorCode;
	private final Object data;
	private final transient Object[] args;

	public BaseException(ErrorCode errorCode) {
		this(errorCode, null);
	}

	public BaseException(ErrorCode errorCode, Object data, Object... args) {
		super(errorCode.formatMessage(args));
		this.errorCode = errorCode;
		this.data = data;
		this.args = args;
	}
}
