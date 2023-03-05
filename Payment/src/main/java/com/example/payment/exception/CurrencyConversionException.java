package com.example.payment.exception;

public class CurrencyConversionException extends PaymentsException
{

	public CurrencyConversionException(String message)
	{
		super("CurrencyConversionException", message);
	}
}
