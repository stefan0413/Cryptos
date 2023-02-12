package com.example.customers.authentication.model;

public record CustomerData(Long customerId,
						   String email,
						   String firstName,
						   String secondName,
						   String lastName,
						   String mobileNumber
						   )
{

}
