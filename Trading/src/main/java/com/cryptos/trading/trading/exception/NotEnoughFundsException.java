package com.cryptos.trading.trading.exception;

public class NotEnoughFundsException extends RuntimeException
{
	public NotEnoughFundsException(String message)
	{
		super(message);
	}
}
