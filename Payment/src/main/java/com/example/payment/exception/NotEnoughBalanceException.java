package com.example.payment.exception;

public class NotEnoughBalanceException extends RuntimeException
{

	public NotEnoughBalanceException(String message)
	{
		super(message);
	}
}
