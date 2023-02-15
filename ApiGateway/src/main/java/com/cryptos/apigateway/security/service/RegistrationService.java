package com.cryptos.apigateway.security.service;

import com.cryptos.apigateway.security.config.JwtUtils;
import com.cryptos.apigateway.security.model.Customer;
import com.cryptos.apigateway.security.model.requests.FinaliseRegistrationRequest;
import com.cryptos.apigateway.security.model.requests.RegistrationRequest;
import com.cryptos.apigateway.security.rest.CustomerRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.logging.Filter;


@Service
public class RegistrationService
{
	Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRestService customerRestService;
	private final JwtUtils jwtUtils;

	public RegistrationService(CustomerRestService customerRestService,
							   JwtUtils jwtUtils)
	{
		this.customerRestService = customerRestService;
		this.jwtUtils = jwtUtils;
	}

	public Long registerCustomer(RegistrationRequest registrationRequest)
	{
		return customerRestService.registerCustomer(getCustomerWithHashedPassword(registrationRequest));
	}

	public String finaliseRegistration(long customerId, FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		System.out.println("FinaliseRegistrationRequest: " + finaliseRegistrationRequest);
		Customer customer = customerRestService.finaliseCustomerRegistration(customerId, finaliseRegistrationRequest);
		System.out.println("hello");

		String token = jwtUtils.generateToken(customer);
		System.out.println("Token: " + token);
		return token;

		//return jwtUtils.generateToken(customer);
	}

	private RegistrationRequest getCustomerWithHashedPassword(RegistrationRequest registrationRequest)
	{
		return new RegistrationRequest(registrationRequest.email(),
									   BCrypt.hashpw(registrationRequest.password(), BCrypt.gensalt(10)));
	}
}
