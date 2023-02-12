package com.example.payment.service;

import com.example.payment.model.CustomerDataResponse;
import com.example.payment.rest.CustomerRestService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentCustomerService
{

	private static final String STRIPE_CUSTOMER_OBJECT = "customer";

	private final CustomerRestService customerRestService;

	public PaymentCustomerService(CustomerRestService customerRestService)
	{
		this.customerRestService = customerRestService;
	}

	public String createCustomer(Long customerId) throws StripeException
	{
		CustomerDataResponse customerData = customerRestService.getCustomerDataById(customerId);

		System.out.println("helos");

		Map<String, Object> customerParameters = new HashMap<>();
		customerParameters.put("id", customerId);
		customerParameters.put("email", customerData.email());
		customerParameters.put("source", "tok_visa");

		return Customer.create(customerParameters).toJson();
	}
}
