package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.NoSuchCustomerException;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerData;
import com.example.customers.authentication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}


	public Customer getCustomerByEmail(String email)
	{

		try
		{
			return customerRepository.getCustomerByEmail(email);
		}
		catch (EmptyResultDataAccessException ex)
		{
			throw new NoSuchCustomerException(String.format("Customer with email: %s is not found", email));
		}
	}

	public Customer getCustomerById(long customerId)
	{
		return customerRepository.getCustomerById(customerId)
								 .orElseThrow(() -> new NoSuchCustomerException(String.format("Customer with customerId: %s is not found", customerId)));
	}

	public CustomerData getCustomerDataById(long customerId)
	{
		List<CustomerData> customerData = customerRepository.getCustomerDataById(customerId);

		if (customerData.isEmpty())
		{
			throw new NoSuchCustomerException(String.format("Customer with customerId: %s is not found", customerId));
		}

		return customerData.get(0);
	}
}
