package com.cryptos.trading.trading.exception;

public class FailedToStoreOrderException extends TradingException
{

	public FailedToStoreOrderException(String message)
	{
		super("FailedToStoreOrderException", message);
	}
}
