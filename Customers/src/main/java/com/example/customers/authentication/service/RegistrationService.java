package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.CustomerAlreadyFinalisedException;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
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
		logger.info("Registration of customer. " + registrationRequest.toString());
		return customerRepository.registerCustomer(registrationRequest);
	}

	public Customer finaliseRegistration(long customerId, FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		logger.info("Finalising registration of customer with customerId=" + customerId);
		ValidationService.validateFinaliseRegistrationRequest(finaliseRegistrationRequest);

		Customer customer = customerService.getCustomerById(customerId);
		validateCustomerAlreadyFinalised(customer);

		customerRepository.finaliseRegistration(customerId, finaliseRegistrationRequest);

		return customerService.getCustomerById(customerId);
	}

	private void validateCustomerAlreadyFinalised(Customer customer)
	{
		if (customer.firstName() != null &&
			customer.secondName() != null &&
			customer.lastName() != null &&
			customer.mobileNumber() != null &&
			customer.active())
		{
			throw new CustomerAlreadyFinalisedException("Customer's registration is already finalised!");
		}
	}
}
