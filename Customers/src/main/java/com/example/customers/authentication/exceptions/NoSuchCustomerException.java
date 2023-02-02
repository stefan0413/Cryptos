package com.example.customers.authentication.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoSuchCustomerException extends AuthenticationException
{

	private final Long customerId;

	public NoSuchCustomerException(String message, Long customerId)
	{
		super(message);
		this.customerId = customerId;
	}

	public String buildFullMessage()
	{
		return getMessage() + " (customerId: " + customerId + ")";
	}
}
