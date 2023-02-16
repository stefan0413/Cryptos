package com.cryptos.apigateway.model;

public record CustomerResponse(long id,
							   String email,
							   String password,
							   String firstName,
							   String secondName,
							   String lastName,
							   String mobileNumber,
							   Boolean active)
{

}
