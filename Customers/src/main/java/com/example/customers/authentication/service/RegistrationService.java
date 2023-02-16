package com.example.customers.authentication.service;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService
{
	private final CustomerRepository customerRepository;
	private final CustomerService customerService;

	public RegistrationService(CustomerRepository customerRepository, CustomerService customerService)
	{
		this.customerRepository = customerRepository;
		this.customerService = customerService;
	}

	public Long registerCustomer(RegistrationRequest registrationRequest)
	{
		ValidationService.validateRegistrationRequest(registrationRequest);

		return customerRepository.registerCustomer(registrationRequest);
	}

	public Customer finaliseRegistration(long customerId, FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		ValidationService.validateFinaliseRegistrationRequest(finaliseRegistrationRequest);

		customerRepository.finaliseRegistration(customerId, finaliseRegistrationRequest);

		return customerService.getCustomerById(customerId);
	}
}
