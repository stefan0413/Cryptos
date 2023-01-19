package com.example.customers.authentication.service;

import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService
{

	private final CustomerRepository customerRepository;

	private final AuthenticationService authenticationService;

	public RegistrationService(CustomerRepository customerRepository, AuthenticationService authenticationService)
	{
		this.customerRepository = customerRepository;
		this.authenticationService = authenticationService;
	}

	public String registerCustomer(RegistrationRequest registrationRequest)
	{
		ValidationService.validateRegistrationRequest(registrationRequest);

		customerRepository.registrateCustomer(getCustomerWithHashedPassword(registrationRequest)).get();

		return logInRegisteredCustomer(registrationRequest);
	}

	private String logInRegisteredCustomer(RegistrationRequest registrationRequest)
	{
		return authenticationService.authenticateCustomer(buildAuthenticationRequest(registrationRequest));
	}

	private AuthenticationRequest buildAuthenticationRequest(RegistrationRequest registrationRequest)
	{
		return new AuthenticationRequest(registrationRequest.email(), registrationRequest.password());
	}


	private RegistrationRequest getCustomerWithHashedPassword(RegistrationRequest registrationRequest)
	{
		return new RegistrationRequest(registrationRequest.email(),
									   BCrypt.hashpw(registrationRequest.password(), BCrypt.gensalt(10)),
									   registrationRequest.firstName(),
									   registrationRequest.secondName(),
									   registrationRequest.lastName(),
									   registrationRequest.mobileNumber());
	}
}
