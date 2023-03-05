package com.example.payment.exception;

public class NoSuchCustomerStripeAccount extends PaymentsException
{
	public NoSuchCustomerStripeAccount(String message)
	{
		super("NoSuchCustomerStripeAccount", message);
	}
}
