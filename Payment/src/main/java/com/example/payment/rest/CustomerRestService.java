package com.example.payment.rest;

import com.example.payment.model.CustomerDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerRestService
{

	private final String GET_CUSTOMER_BY_ID_URL;

	private final RestTemplate restTemplate = new RestTemplate();

	public CustomerRestService(@Value("${customers.get-customer-by-id-url}") String GET_CUSTOMER_BY_ID_URL)
	{
		this.GET_CUSTOMER_BY_ID_URL = GET_CUSTOMER_BY_ID_URL;
	}

	public CustomerDataResponse getCustomerDataById(long customerId)
	{
		return restTemplate.getForObject(GET_CUSTOMER_BY_ID_URL, CustomerDataResponse.class, customerId);
	}
}
