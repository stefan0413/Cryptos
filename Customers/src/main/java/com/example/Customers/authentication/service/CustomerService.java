package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.NoSuchCustomerException;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService
{

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email)
	{
		return customerRepository.getCustomerByEmail(email)
								 .orElseThrow(() -> new UsernameNotFoundException("A customer with email: " + email + ",  does not exist"));
	}

	public Customer getCustomerById(long customerId)
	{
		return customerRepository.getCustomerById(customerId)
								 .orElseThrow(() -> new NoSuchCustomerException("A customer with id: " + customerId + ",  does not exist"));
	}
}
