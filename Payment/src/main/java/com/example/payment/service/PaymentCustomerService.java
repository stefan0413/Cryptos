package com.example.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentCustomerService
{

	private static final String STRIPE_CUSTOMER_OBJECT = "customer";

	public String createCustomer(Long customerId, String currency, String email) throws StripeException
	{

		Map<String, Object> customerParameters = new HashMap<>();
		customerParameters.put("id", customerId);
		customerParameters.put("email", "gosho@gmail.com");
		customerParameters.put("source", "tok_visa");

		return Customer.create(customerParameters).toJson();
	}
}
