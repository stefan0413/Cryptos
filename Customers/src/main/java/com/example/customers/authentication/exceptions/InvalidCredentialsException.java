package com.example.customers.authentication.exceptions;

public class InvalidCredentialsException extends CustomerServiceException
{

	public InvalidCredentialsException(String message)

	{
		super("InvalidCredentialsException",message);
	}
}
