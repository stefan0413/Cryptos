package com.example.customers.authentication.exceptions;

import java.util.function.Supplier;

public class NoSuchCustomerException extends RuntimeException
{

	public NoSuchCustomerException(String message)
	{
		super(message);
	}
}
