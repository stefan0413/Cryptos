package com.cryptos.apigateway.service.customers;

import com.cryptos.apigateway.jwt.JwtUtils;
import com.cryptos.apigateway.model.customers.Customer;
import com.cryptos.apigateway.model.customers.FinaliseRegistrationRequest;
import com.cryptos.apigateway.model.customers.RegistrationRequest;
import com.cryptos.apigateway.rest.customers.CustomerRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService
{

	private static final String VALID_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
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
		isValidPassword(registrationRequest.password());
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

	private void isValidPassword(String password)
	{
		if (!password.matches(VALID_PASSWORD_REGEX))
		{
			throw new IllegalArgumentException("Password must be between 8 and 20 characters long and contain at least one digit, one lower case letter, one upper case letter and one special character");
		}
	}
}
