package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.service.RegistrationService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/customers/authentication")
public class CustomerAuthenticationController
{

	private final RegistrationService registrationService;


	public CustomerAuthenticationController(RegistrationService registrationService)
	{
		this.registrationService = registrationService;
	}

	@PostMapping("/register")
	public Long registerCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registerCustomer(registrationRequest);
	}

	@PostMapping("/{customerId}/finalise-registration")
	public Customer finaliseRegistration(@PathVariable Long customerId,
										 @RequestBody FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		return registrationService.finaliseRegistration(customerId, finaliseRegistrationRequest);
	}
}
