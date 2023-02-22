package com.cryptos.apigateway.rest.payments;

import com.cryptos.apigateway.model.payments.CustomerPaymentMethodResponseWrapper;
import com.cryptos.apigateway.model.payments.CustomerStripeAccount;
import com.cryptos.apigateway.rest.ResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CustomerStripeAccountRestService
{

	private final String GET_FULL_CUSTOMER;
	private final String GET_CUSTOMER_PAYMENT_METHODS;
	private final String CREATE_CUSTOMER_STRIPE_ACCOUNT;
	private final String ADD_PAYMENT_METHOD_TO_CUSTOMER;
	private final RestTemplate restTemplate = new RestTemplate();

	public CustomerStripeAccountRestService(@Value("${payments.customer.get-full-customer}") String getFullCustomer,
											@Value("${payments.customer.get-customer-payment-methods}") String getCustomerPaymentMethods,
											@Value("${payments.customer.create-customer-stripe-account}") String createCustomerStripeAccount,
											@Value("${payments.customer.add-payment-method-to-customer}") String addPaymentMethodToCustomer)
	{
		GET_FULL_CUSTOMER = getFullCustomer;
		GET_CUSTOMER_PAYMENT_METHODS = getCustomerPaymentMethods;
		CREATE_CUSTOMER_STRIPE_ACCOUNT = createCustomerStripeAccount;
		ADD_PAYMENT_METHOD_TO_CUSTOMER = addPaymentMethodToCustomer;

		restTemplate.setErrorHandler(new ResponseErrorHandler());
	}


	public CustomerStripeAccount getFullCustomer(long customerId)
	{
		return restTemplate.getForObject(GET_FULL_CUSTOMER, CustomerStripeAccount.class, customerId);
	}

	public CustomerPaymentMethodResponseWrapper getCustomerPaymentMethods(long customerId)
	{
		return restTemplate.getForObject(GET_CUSTOMER_PAYMENT_METHODS, CustomerPaymentMethodResponseWrapper.class, customerId);
	}

	public void createCustomerStripeAccount(Long customerId, String currencyCode)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(CREATE_CUSTOMER_STRIPE_ACCOUNT)
														   .queryParam("currencyCode", currencyCode);

		restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId)).toUriString(),null,
				Void.class);
	}

	public void addPaymentMethodToCustomer(long customerId, String paymentMethodToken)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ADD_PAYMENT_METHOD_TO_CUSTOMER)
														   .queryParam("paymentMethodToken", paymentMethodToken);

		restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId)).toUriString(),null,
				Void.class);
	}
}
