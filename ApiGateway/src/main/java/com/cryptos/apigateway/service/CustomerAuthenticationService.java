package com.cryptos.apigateway.service;

import com.cryptos.apigateway.exceptions.CustomerException;
import com.cryptos.apigateway.rest.CustomerRestService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerAuthenticationService implements UserDetailsService
{

	private final CustomerRestService customerRestService;

	public CustomerAuthenticationService(CustomerRestService customerRestService)
	{
		this.customerRestService = customerRestService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return customerRestService.getCustomerDataByEmail(username);
	}
}
