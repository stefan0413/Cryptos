package com.example.customers.authentication.exceptions;

public class InvalidCredentialsException extends RuntimeException
{

	public InvalidCredentialsException(String message)
	{
		super(message);
	}
}
