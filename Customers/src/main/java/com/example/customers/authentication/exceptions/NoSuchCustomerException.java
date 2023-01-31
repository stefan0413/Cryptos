package com.example.customers.authentication.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoSuchCustomerException extends AuthenticationException
{

	public NoSuchCustomerException(String message)
	{
		super(message);
	}
}
