package com.cryptos.apigateway.security.rest;

import com.cryptos.apigateway.security.model.Customer;
import com.cryptos.apigateway.security.model.requests.FinaliseRegistrationRequest;
import com.cryptos.apigateway.security.model.requests.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Filter;

@Service
public class CustomerRestService
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RestTemplate restTemplate = new RestTemplate();


	public Customer getCustomerDataByEmail(String email)
	{
		String url = "http://localhost:9090/private/customers/{email}";

		Customer customer = restTemplate.getForObject(url, Customer.class, email);
		logger.info(customer.toString());

		return customer;
	}

	public Long registerCustomer(RegistrationRequest registrationRequest)
	{
		String url = "http://localhost:9090/private/customers/authentication/register";

		return restTemplate.postForObject(url, registrationRequest, Long.class);
	}

	public Customer finaliseCustomerRegistration(Long customerId, FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		String url = "http://localhost:9090/private/customers/authentication/{customerId}/finalise-registration";

		return restTemplate.postForObject(url, finaliseRegistrationRequest, Customer.class, customerId);
	}
}
