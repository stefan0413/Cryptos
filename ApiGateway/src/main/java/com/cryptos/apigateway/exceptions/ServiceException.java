package com.cryptos.apigateway.exceptions;

public class ServiceException extends RuntimeException
{
	private final String type;
	public ServiceException(String type, String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
