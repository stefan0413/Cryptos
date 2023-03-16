package com.cryptos.marketdata.exception;

public class BinanceException extends RuntimeException
{
	private final String type;
	public BinanceException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
