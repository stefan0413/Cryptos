package com.example.customers.authentication.service;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService
{

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}

	public Customer getCustomerById(long customerId)
	{
		return customerRepository.getCustomerById(customerId).orElseGet(()-> null);
	}
}
