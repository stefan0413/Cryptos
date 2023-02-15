package com.cryptos.apigateway.security.model.requests;

public record AuthenticationRequest(String email,
									String password)
{

}
