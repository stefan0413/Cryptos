package com.cryptos.apigateway.rest.customers;

import com.cryptos.apigateway.model.customers.Customer;
import com.cryptos.apigateway.model.customers.FinaliseRegistrationRequest;
import com.cryptos.apigateway.model.customers.RegistrationRequest;
import com.cryptos.apigateway.rest.ServicesResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerRestService
{

	private final String GET_CUSTOMER_BY_EMAIL_URL;
	private final String REGISTER_URL;
	private final String FINALISE_REGISTRATION_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public CustomerRestService(@Value("${customers.get-by-email-url}") String getCustomerByEmail,
							   @Value("${customers.register-url}") String registerUrl,
							   @Value("${customers.finalise-registration-url}") String finaliseRegistrationUrl)
	{
		GET_CUSTOMER_BY_EMAIL_URL = getCustomerByEmail;
		REGISTER_URL = registerUrl;
		FINALISE_REGISTRATION_URL = finaliseRegistrationUrl;
		restTemplate.setErrorHandler(new ServicesResponseErrorHandler());
	}


	public Customer getCustomerDataByEmail(String email)
	{
		return restTemplate.getForObject(GET_CUSTOMER_BY_EMAIL_URL, Customer.class, email);
	}

	public Long registerCustomer(RegistrationRequest registrationRequest)
	{
		return restTemplate.postForObject(REGISTER_URL, registrationRequest, Long.class);
	}

	public Customer finaliseCustomerRegistration(Long customerId, FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		return restTemplate.postForObject(FINALISE_REGISTRATION_URL, finaliseRegistrationRequest, Customer.class, customerId);
	}
}
