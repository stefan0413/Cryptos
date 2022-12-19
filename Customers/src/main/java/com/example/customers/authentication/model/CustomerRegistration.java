package com.example.customers.authentication.model;

public record CustomerRegistration(
		String email,
		String password,
		String firstName,
		String secondName,
		String lastName,
		String mobileNumber
)
{

}
