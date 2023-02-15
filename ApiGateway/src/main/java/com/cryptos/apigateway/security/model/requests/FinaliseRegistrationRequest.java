package com.cryptos.apigateway.security.model.requests;

public record FinaliseRegistrationRequest(String firstName,
										  String secondName,
										  String lastName,
										  String mobileNumber)
{

}
