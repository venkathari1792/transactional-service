package com.n26.errors;

import org.springframework.http.HttpStatus;

public enum ResponseCode {

	TRANSACTION_POSTED(HttpStatus.CREATED, "Transaction Posted"),

	JSON_MAPPING_ERROR(HttpStatus.BAD_REQUEST, "Invalid JSON"),

	OLD_TRANSACTION(HttpStatus.NO_CONTENT, "No Transaction Found"),

	PARSE_EXCEPTION(HttpStatus.UNPROCESSABLE_ENTITY, "Parse Exception Occured"),

	TRANSACTIONS_DELETED(HttpStatus.NO_CONTENT, "All Transactions Deleted"),;

	private HttpStatus status;

	private String message;

	private ResponseCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;

	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
