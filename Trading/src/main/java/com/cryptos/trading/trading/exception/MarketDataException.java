package com.cryptos.trading.trading.exception;

public class MarketDataException extends RuntimeException
{
	private final String type;
	public MarketDataException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
