package com.banco.financeiro.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 8252284119341958749L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
