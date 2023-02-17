package com.example.payment.rest;

import com.example.payment.model.CustomerDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerRestService
{

	private final RestTemplate restTemplate = new RestTemplate();

	public CustomerDataResponse getCustomerDataById(long customerId)
	{
		String url = "http://localhost:9090/api/customers/data/{customerId}";
		CustomerDataResponse customerDataResponse = restTemplate.getForObject(url, CustomerDataResponse.class, customerId);

		return customerDataResponse;
	}
}
