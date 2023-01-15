package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.service.AuthenticationService;
import com.example.customers.authentication.service.RegistrationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerAuthenticationController
{
	private final RegistrationService registrationService;
	private final AuthenticationService authenticationService;

	public CustomerAuthenticationController(RegistrationService registrationService, AuthenticationService authenticationService)
	{
		this.registrationService = registrationService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authentication")
	public String authenticate(@RequestBody AuthenticationRequest authenticationRequest)
	{
		return authenticationService.authenticateCustomer(authenticationRequest);
	}

	@PostMapping("/register")
	public String registrateCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registerCustomer(registrationRequest);
	}
}
