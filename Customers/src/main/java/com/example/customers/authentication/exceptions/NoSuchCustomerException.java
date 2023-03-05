package com.example.customers.authentication.exceptions;

public class NoSuchCustomerException extends CustomerServiceException
{

	public NoSuchCustomerException(String message)
	{
		super("NoSuchCustomerException", message);
	}

}
