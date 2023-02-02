package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.service.AuthenticationService;
import com.example.customers.authentication.service.CustomerService;
import com.example.customers.authentication.service.RegistrationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/authentication")
public class CustomerAuthenticationController
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistrationService registrationService;
	private final AuthenticationService authenticationService;

	public CustomerAuthenticationController(RegistrationService registrationService,
											AuthenticationService authenticationService)
	{
		this.registrationService = registrationService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthenticationRequest authenticationRequest)
	{
		return authenticationService.authenticateCustomer(authenticationRequest);
	}

	@PostMapping("/register")
	public Long registrateCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registerCustomer(registrationRequest);
	}

	@PostMapping("/{customerId}/finalise-registration")
	public String finaliseRegistration(@PathVariable Long customerId,
									   @RequestBody FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		return registrationService.finaliseRegistration(customerId, finaliseRegistrationRequest);
	}
}
