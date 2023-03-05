package com.example.customers.authentication.exceptions;

public class CustomerServiceException extends RuntimeException
{
	private final String type;

	public CustomerServiceException(String type, String message)
	{
		super(message);

		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
