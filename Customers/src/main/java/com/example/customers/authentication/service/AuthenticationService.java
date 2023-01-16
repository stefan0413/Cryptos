package com.example.customers.authentication.service;

import com.example.customers.authentication.config.JwtUtils;
import com.example.customers.authentication.model.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{

	private final AuthenticationManager authenticationManager;
	private final CustomerService customerService;
	private final JwtUtils jwtUtils;

	public AuthenticationService(AuthenticationManager authenticationManager, CustomerService customerService, JwtUtils jwtUtils)
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
