package com.cryptos.apigateway.controllers;

import com.cryptos.apigateway.exceptions.CustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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

	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<String> handleClientBasedException(CustomerException e)
	{
		logger.warn("Customers service exception - " + e.getType() + " " + e.getMessage());
		return ResponseEntity.badRequest().header("REASON",e.getType()).body(e.getMessage());
	}
}
