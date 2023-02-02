package com.example.customers.authentication.service;

import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService
{
	Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRepository customerRepository;

	private final AuthenticationService authenticationService;
	private final CustomerService customerService;

	public RegistrationService(CustomerRepository customerRepository,
							   AuthenticationService authenticationService,
							   CustomerService customerService)
	{
		this.customerRepository = customerRepository;
		this.authenticationService = authenticationService;
		this.customerService = customerService;
	}

	public Long registerCustomer(RegistrationRequest registrationRequest)
	{
		ValidationService.validateRegistrationRequest(registrationRequest);

		return customerRepository.registrateCustomer(getCustomerWithHashedPassword(registrationRequest)).get().id();
	}

	public String finaliseRegistration(long customerId, FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		ValidationService.validateFinaliseRegistrationRequest(finaliseRegistrationRequest);

		customerRepository.finaliseRegistration(customerId, finaliseRegistrationRequest);

		return logInRegisteredCustomer(customerId);
	}

	private String logInRegisteredCustomer(long customerId)
	{
		Customer customer = customerService.getCustomerById(customerId);
		logger.info("Authentication request: " + customer.getUsername() + "\n " + customer.getPassword());
		return authenticationService.authenticateCustomer(new AuthenticationRequest(customer.getUsername(),
																					customer.getPassword()));
	}

	private RegistrationRequest getCustomerWithHashedPassword(RegistrationRequest registrationRequest)
	{
		return new RegistrationRequest(registrationRequest.email(),
									   BCrypt.hashpw(registrationRequest.password(), BCrypt.gensalt(10)));
	}
}
