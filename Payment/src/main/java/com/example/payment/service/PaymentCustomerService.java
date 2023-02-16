package com.example.payment.service;

import com.example.payment.model.CustomerDataResponse;
import com.example.payment.rest.CustomerRestService;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentCustomerService
{

	private final CustomerRestService customerRestService;

	public PaymentCustomerService(CustomerRestService customerRestService)
	{
		this.customerRestService = customerRestService;
	}

	public String createCustomer(Long customerId) throws StripeException
	{
		CustomerDataResponse customerData = customerRestService.getCustomerDataById(customerId);

		Map<String, Object> customerParameters = new HashMap<>();
		customerParameters.put("id", customerId);
		customerParameters.put("email", customerData.email());

		return Customer.create(customerParameters).toJson();
	}

	public String addPaymentMethodToCustomer(long customerId, String methodToken) throws StripeException
	{

		List<String> expandList = List.of("sources");
		Map<String, Object> retrieveParams = Map.of("expand", expandList);

		Customer customer = Customer.retrieve(String.valueOf(customerId), retrieveParams, null);

		Map<String, Object> params = Map.of("source", methodToken);

		Card card = (Card) customer.getSources().create(params);

		return card.toJson();
	}
}
