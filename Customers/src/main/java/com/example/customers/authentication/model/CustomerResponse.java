package com.example.customers.authentication.model;

public record CustomerResponse(boolean isSuccessful,
							   String value,
							   String message)
{


}
