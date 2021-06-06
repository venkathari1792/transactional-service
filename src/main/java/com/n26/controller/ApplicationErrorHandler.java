package com.n26.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.n26.exception.ApplicationException;
import com.n26.response.EmptyJson;

@ControllerAdvice
public class ApplicationErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		if (ex.getRootCause() instanceof ApplicationException) {
			ApplicationException e = (ApplicationException) ex.getRootCause();
			return handleApplicationExceptions(e, request);
		}

		return handleExceptionInternal(ex, new EmptyJson(), httpHeaders, status, request);
	}

	@ExceptionHandler({ ApplicationException.class })
	protected ResponseEntity<Object> handleApplicationExceptions(final ApplicationException e,
			final WebRequest request) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, new EmptyJson(), httpHeaders, e.getStatus().getStatus(), request);
	}

}
