package com.cryptos.trading.trading.exception;

public class TradingException extends RuntimeException
{
	String type;
	public TradingException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
