package com.example.customers.authentication.controller;

import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.exceptions.NoSuchCustomerException;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler
{

	Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> unhandledException(Exception e)
	{
		logger.info("Unhandled Exception", e);
		return ResponseEntity.internalServerError().header("REASON","UnhandledException").body(e.getMessage());
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleClientBasedException(InvalidCredentialsException e)
	{
		logger.warn("Client based exception", e);
		return ResponseEntity.badRequest().header("REASON","Client Error").body(e.getMessage());
	}

	@ExceptionHandler(NoSuchCustomerException.class)
	public ResponseEntity<String> handleAuthenticationException( NoSuchCustomerException e)
	{
		logger.warn("No such customer exception");
		return ResponseEntity.status(400).header("REASON","InvalidCredentials").body(e.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityException(DataIntegrityViolationException e)
	{
		logger.warn("DataIntegrityViolationException: " + e.getMessage());
		return ResponseEntity.status(400).header("REASON","DataIntegrityViolationException").body(e.getMessage());
	}
}
