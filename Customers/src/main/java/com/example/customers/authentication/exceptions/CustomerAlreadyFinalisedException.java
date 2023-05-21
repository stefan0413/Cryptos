package com.example.customers.authentication.exceptions;

public class CustomerAlreadyFinalisedException extends CustomerServiceException
{

	public CustomerAlreadyFinalisedException(String message)

	{
		super("CustomerAlreadyFinalisedException", message);
	}
}
