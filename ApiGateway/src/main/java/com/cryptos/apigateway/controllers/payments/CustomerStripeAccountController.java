package com.cryptos.apigateway.controllers.payments;

import com.cryptos.apigateway.model.payments.CustomerPaymentMethodResponseWrapper;
import com.cryptos.apigateway.model.payments.CustomerStripeAccount;
import com.cryptos.apigateway.service.payments.CustomerStripeAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/customer-stripe-accounts/{customerId}")
public class CustomerStripeAccountController
{
	private final CustomerStripeAccountService customerStripeAccountService;

	public CustomerStripeAccountController(CustomerStripeAccountService customerStripeAccountService)
	{
		this.customerStripeAccountService = customerStripeAccountService;
	}

	@GetMapping
	public CustomerStripeAccount getFullCustomerStripeAccount(@PathVariable Long customerId)
	{
		return customerStripeAccountService.getCustomerStripeAccount(customerId);
	}

	@GetMapping("/payment-methods")
	public CustomerPaymentMethodResponseWrapper getCustomerPaymentMethods(@PathVariable long customerId)
	{
		return customerStripeAccountService.getCustomerPaymentMethods(customerId);
	}

	@PostMapping("/create")
	public void createCustomerStripeAccount(@PathVariable Long customerId,
											@RequestParam String currencyCode)
	{
		System.out.println("CurrencyCode:" + currencyCode);
		customerStripeAccountService.createCustomerStripeAccount(customerId, currencyCode);
	}

	@PostMapping("/payment-methods/add")
	public void addPaymentMethodToCustomer(@PathVariable long customerId,
										   @RequestParam String paymentMethodToken)
	{
		customerStripeAccountService.addPaymentMethodToCustomer(customerId, paymentMethodToken);
	}
}
