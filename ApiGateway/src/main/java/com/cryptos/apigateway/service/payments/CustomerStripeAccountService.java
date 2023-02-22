package com.cryptos.apigateway.service.payments;

import com.cryptos.apigateway.model.payments.CustomerPaymentMethodResponseWrapper;
import com.cryptos.apigateway.model.payments.CustomerStripeAccount;
import com.cryptos.apigateway.rest.payments.CustomerStripeAccountRestService;
import org.springframework.stereotype.Service;

@Service
public class CustomerStripeAccountService
{

	private final CustomerStripeAccountRestService customerStripeAccountRestService;

	public CustomerStripeAccountService(CustomerStripeAccountRestService customerStripeAccountRestService)
	{
		this.customerStripeAccountRestService = customerStripeAccountRestService;
	}

	public CustomerStripeAccount getCustomerStripeAccount(long customerId)
	{
		return customerStripeAccountRestService.getFullCustomer(customerId);
	}

	public CustomerPaymentMethodResponseWrapper getCustomerPaymentMethods(long customerId)
	{
		return customerStripeAccountRestService.getCustomerPaymentMethods(customerId);
	}

	public void createCustomerStripeAccount(long customerId, String currencyCode)
	{
		customerStripeAccountRestService.createCustomerStripeAccount(customerId, currencyCode);
	}

	public void addPaymentMethodToCustomer(long customerId, String paymentMethodToken)
	{
		customerStripeAccountRestService.addPaymentMethodToCustomer(customerId, paymentMethodToken);
	}
}
