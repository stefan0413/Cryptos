package com.cryptos.apigateway.exceptions;

public class PaymentsException extends RuntimeException
{
	private final String type;
	public PaymentsException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
