package com.banco.financeiro.exception.system;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.banco.financeiro.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error("MethodArgumentNotValidException - Message: {}", ex);

		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(value -> value.getDefaultMessage())
				.toList();

		Response<Void> response = new Response<>();
		response.setErrors(errors);

		return ResponseEntity.status(status).body(response);
	}
}
