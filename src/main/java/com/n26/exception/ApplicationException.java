package com.n26.exception;

import com.n26.errors.ResponseCode;

public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2263275687531145994L;

	private ResponseCode status;

	private String message;

	public ApplicationException(ResponseCode status) {
		super();
		this.status = status;
	}

	public ApplicationException(ResponseCode status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResponseCode getStatus() {
		return status;
	}

	public void setStatus(ResponseCode status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
