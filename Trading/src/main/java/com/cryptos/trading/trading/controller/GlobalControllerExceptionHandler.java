package com.cryptos.trading.trading.controller;

import com.cryptos.trading.trading.exception.PaymentsException;
import com.cryptos.trading.trading.exception.TradingException;
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
		return ResponseEntity.internalServerError().header("REASON", "UnhandledException").body(e.getMessage());
	}

	@ExceptionHandler(TradingException.class)
	public ResponseEntity<String> handleStripeException(TradingException e)
	{
		logger.warn("TradingException: " + e.getMessage());
		return ResponseEntity.internalServerError().header("REASON", e.getType()).body(e.getMessage());
	}

	@ExceptionHandler(PaymentsException.class)
	public ResponseEntity<String> handlePaymentsException(PaymentsException e)
	{
		logger.warn("Payments exception: " + e.getMessage());
		return ResponseEntity.internalServerError().header("REASON", e.getType()).body(e.getMessage());
	}
}