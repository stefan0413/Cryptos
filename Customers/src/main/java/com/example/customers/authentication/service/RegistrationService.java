package com.example.customers.authentication.service;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService
{

	private CustomerRepository customerRepository;

	public RegistrationService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}

	public Customer registrateCustomer(RegistrationRequest registrationRequest)
	{
		ValidationService.validateRegistrationRequest(registrationRequest);

		System.out.println(registrationRequest);

		return customerRepository.registrateCustomer(getCustomerWithHashedPassword(registrationRequest)).get();
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
