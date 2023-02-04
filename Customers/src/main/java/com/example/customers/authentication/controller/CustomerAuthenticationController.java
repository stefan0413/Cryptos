package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.CustomerResponse;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.service.AuthenticationService;
import com.example.customers.authentication.service.RegistrationService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/authentication")
public class CustomerAuthenticationController
{

	private final RegistrationService registrationService;
	private final AuthenticationService authenticationService;

	public CustomerAuthenticationController(RegistrationService registrationService,
											AuthenticationService authenticationService)
	{
		this.registrationService = registrationService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authenticate")
	public CustomerResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest)
	{
		return buildCustomerResponse(authenticationService.authenticateCustomer(authenticationRequest),
									 "Successfully authenticated");

	}

	@PostMapping("/register")
	public CustomerResponse registerCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return buildCustomerResponse(registrationService.registerCustomer(registrationRequest).toString(),
									 "Successfull registration");
	}

	@PostMapping("/{customerId}/finalise-registration")
	public CustomerResponse finaliseRegistration(@PathVariable Long customerId,
												 @RequestBody FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		return buildCustomerResponse(registrationService.finaliseRegistration(customerId, finaliseRegistrationRequest),
									 "Successfully completed the registration");
	}

	private CustomerResponse buildCustomerResponse(String value, String message)
	{
		return new CustomerResponse(true, value, message);
	}
}
