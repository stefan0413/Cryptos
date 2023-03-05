package com.example.payment.exception;

public class NotEnoughBalanceException extends PaymentsException
{

	public NotEnoughBalanceException(String message)
	{
		super("NotEnoughBalance", message);
	}
}
