package com.cryptos.apigateway.exceptions;

public class CustomerException extends RuntimeException
{
	private final String type;
	public CustomerException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
