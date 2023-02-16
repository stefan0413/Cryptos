package com.cryptos.apigateway.service;

import com.cryptos.apigateway.jwt.JwtUtils;
import com.cryptos.apigateway.model.requests.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{

	private final AuthenticationManager authenticationManager;
	private final CustomerAuthenticationService customerService;
	private final JwtUtils jwtUtils;

	public AuthenticationService(AuthenticationManager authenticationManager,
								 CustomerAuthenticationService customerService,
								 JwtUtils jwtUtils)
	{
		this.authenticationManager = authenticationManager;
		this.customerService = customerService;
		this.jwtUtils = jwtUtils;
	}

	public String authenticateCustomer(AuthenticationRequest authenticationRequest)
	{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.email(),
														authenticationRequest.password()));

		final UserDetails customer = customerService.loadUserByUsername(authenticationRequest.email());

		return jwtUtils.generateToken(customer);

	}
}
