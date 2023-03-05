package com.cryptos.trading.trading.exception;

public class CurrencyConversionException extends TradingException
{
	public CurrencyConversionException(String message)
	{
		super("CurrencyConversionException", message);
	}
}
