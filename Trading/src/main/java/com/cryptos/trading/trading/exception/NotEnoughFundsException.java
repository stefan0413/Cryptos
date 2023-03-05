package com.cryptos.trading.trading.exception;

public class NotEnoughFundsException extends TradingException
{

	public NotEnoughFundsException(String message)
	{
		super("NotEnoughFundsException", message);
	}
}
