package com.cryptos.trading.trading.exception;

public class CurrencyConversionException extends RuntimeException
{
	private final String type;
	public CurrencyConversionException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
