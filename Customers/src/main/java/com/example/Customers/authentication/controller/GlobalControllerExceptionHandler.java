package com.example.customers.authentication.controller;

import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.exceptions.NoSuchCustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler
{

	Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> unhandledException(Exception e)
	{
		logger.error("Unhandled Exception", e);
		return ResponseEntity.internalServerError().body(buildError("Unhandled Exception", e.getMessage()));
	}

	@ExceptionHandler({InvalidCredentialsException.class, NoSuchCustomerException.class})
	public ResponseEntity handleClientBasedException(Exception e)
	{
		logger.warn("Client based exception", e);
		return ResponseEntity.badRequest().body(buildError("Client Error", e.getMessage()));
	}

	private Map<String, Object> buildError(String type, String message)
	{
		return Map.of("type", type,
					  "message", message);
	}
}
