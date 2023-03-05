package com.cryptos.apigateway.controllers;

import com.cryptos.apigateway.exceptions.ServiceException;
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

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<String> handleClientBasedException(ServiceException e)
	{
		logger.warn("Service exception - " + e.getType() + " " + e.getMessage());
		return ResponseEntity.badRequest().header("REASON",e.getType()).body(e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgimentException(IllegalArgumentException e)
	{
		logger.warn("Service exception - " + "IllegalArgumentException " + e.getMessage());
		return ResponseEntity.badRequest().header("REASON","IllegalArgumentException").body(e.getMessage());
	}
}
