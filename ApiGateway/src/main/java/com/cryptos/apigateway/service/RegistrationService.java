package com.cryptos.apigateway.service;

import com.cryptos.apigateway.jwt.JwtUtils;
import com.cryptos.apigateway.model.Customer;
import com.cryptos.apigateway.model.requests.FinaliseRegistrationRequest;
import com.cryptos.apigateway.model.requests.RegistrationRequest;
import com.cryptos.apigateway.rest.CustomerRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


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
		Customer customer = customerRestService.finaliseCustomerRegistration(customerId, finaliseRegistrationRequest);

		return jwtUtils.generateToken(customer);
	}

	private RegistrationRequest getCustomerWithHashedPassword(RegistrationRequest registrationRequest)
	{
		return new RegistrationRequest(registrationRequest.email(),
									   BCrypt.hashpw(registrationRequest.password(), BCrypt.gensalt(10)));
	}
}
