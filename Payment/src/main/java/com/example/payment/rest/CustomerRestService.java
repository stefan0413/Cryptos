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
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerDataResponse getCustomerDataById(long customerId)
	{
		String url = "http://localhost:9090/api/customers/data/{customerId}";
		logger.info("hello");
		CustomerDataResponse customerDataResponse = restTemplate.getForObject(url, CustomerDataResponse.class, customerId);
		logger.info(customerDataResponse.toString());

		return customerDataResponse;
	}
}
