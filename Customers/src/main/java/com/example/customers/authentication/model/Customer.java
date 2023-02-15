package com.example.customers.authentication.model;

public record Customer(long id,
					   String email,
					   String password,
					   String firstName,
					   String secondName,
					   String lastName,
					   String mobileNumber,
					   Boolean active)
{

}
