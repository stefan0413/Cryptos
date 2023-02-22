package com.example.payment.controller;

import com.example.payment.exception.PaymentsException;
import com.stripe.exception.StripeException;
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

	@ExceptionHandler(StripeException.class)
	public ResponseEntity<String> handleStripeException(StripeException e)
	{
		logger.warn("StripeException", e);
		return ResponseEntity.internalServerError().header("REASON","StripeException").body(e.getMessage());
	}

	@ExceptionHandler(PaymentsException.class)
	public ResponseEntity<String> handlePaymentsException( PaymentsException e)
	{
		logger.warn("Payments exception");
		return ResponseEntity.internalServerError().header("REASON",e.type).body(e.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> DataIntegrityViolationException( DataIntegrityViolationException e)
	{
		logger.warn("Payments exception");
		return ResponseEntity.internalServerError().header("REASON",e.getCause().toString()).body(e.getMessage());
	}
}
