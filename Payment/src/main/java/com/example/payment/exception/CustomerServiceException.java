package com.example.payment.exception;

public class CustomerServiceException extends RuntimeException
{
	public String type;
	public CustomerServiceException(String type,String message)
	{
		super(message);
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
