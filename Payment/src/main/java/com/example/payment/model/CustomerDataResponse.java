package com.example.payment.model;

public record CustomerDataResponse(Long customerId,
						   String email,
						   String firstName,
						   String secondName,
						   String lastName,
						   String mobileNumber)
{

}

