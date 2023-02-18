package com.example.payment.service;

import com.example.payment.model.CustomerDataResponse;
import com.example.payment.model.CustomerStripeAccountRequest;
import com.example.payment.repository.CustomerStripeAccountRepository;
import com.example.payment.rest.CustomerRestService;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentCustomerService
{

	private final CustomerRestService customerRestService;
	private final CustomerStripeAccountRepository customerStripeAccountRepository;

	public PaymentCustomerService(CustomerRestService customerRestService, CustomerStripeAccountRepository customerStripeAccountRepository)
	{
		this.customerRestService = customerRestService;
		this.customerStripeAccountRepository = customerStripeAccountRepository;
	}

	public void createCustomer(Long customerId) throws StripeException
	{
		CustomerDataResponse customerData = customerRestService.getCustomerDataById(customerId);
		System.out.println("CustomerData:" + customerData.toString());
		createStripeCustomer(customerData);
		saveCustomerStripeAccount(customerData);
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

	private void createStripeCustomer(CustomerDataResponse customerData) throws StripeException
	{
		Map<String, Object> customerParameters = new HashMap<>();
		customerParameters.put("id", customerData.customerId());
		customerParameters.put("email", customerData.email());
		customerParameters.put("name", getFullName(customerData).trim());

		Customer.create(customerParameters);
	}

	private void saveCustomerStripeAccount(CustomerDataResponse customerData)
	{
		CustomerStripeAccountRequest customerStripeAccountRequest =
				new CustomerStripeAccountRequest(customerData.customerId(),
												 BigDecimal.ZERO,
												 BigDecimal.ZERO,
												 customerData.email(),
												 getFullName(customerData).trim());

		customerStripeAccountRepository.save(customerStripeAccountRequest);
	}

	private static String getFullName(CustomerDataResponse customerDataResponse)
	{
		return removeNullNames(customerDataResponse.firstName()) +
			   removeNullNames(customerDataResponse.secondName()) +
			   removeNullNames(customerDataResponse.lastName());
	}

	private static String removeNullNames(String name)
	{
		return name != null ? name + " " : "";
	}
}
