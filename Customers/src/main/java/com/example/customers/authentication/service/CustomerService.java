package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.NoSuchCustomerException;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerData;
import com.example.customers.authentication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements UserDetailsService
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
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
								 .orElseThrow(() -> new NoSuchCustomerException("No such customer!", customerId));
	}

	public CustomerData getCustomerDataById(long customerId)
	{
		List<CustomerData> customerData = customerRepository.getCustomerDataById(customerId);

		if (customerData.isEmpty())
		{
			throw new NoSuchCustomerException("No such customer!", customerId);
		}

		return customerData.get(0);
	}
}
