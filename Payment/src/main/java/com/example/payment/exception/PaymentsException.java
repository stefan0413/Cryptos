package com.example.payment.exception;

public class PaymentsException extends RuntimeException
{
	public String type;
	public PaymentsException(String reason, String message)
	{
		super(message);
		this.type = reason;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
