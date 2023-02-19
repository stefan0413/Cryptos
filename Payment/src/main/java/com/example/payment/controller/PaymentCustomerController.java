package com.example.payment.controller;

import com.example.payment.model.CustomerPaymentMethod;
import com.example.payment.model.CustomerStripeAccount;
import com.example.payment.service.CustomerStripeAccountService;
import com.example.payment.service.PaymentMethodService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/private/payments/customer-stripe-accounts/{customerId}")
public class PaymentCustomerController
{

	private final CustomerStripeAccountService customerStripeAccountService;
	private final PaymentMethodService paymentMethodService;

	public PaymentCustomerController(CustomerStripeAccountService customerStripeAccountService, PaymentMethodService paymentMethodService)
	{
		this.customerStripeAccountService = customerStripeAccountService;
		this.paymentMethodService = paymentMethodService;
	}

	@GetMapping
	public CustomerStripeAccount getFullCustomerStripeAccount(@PathVariable Long customerId)
	{
		return customerStripeAccountService.getFullCustomer(customerId);
	}

	@PostMapping("/create")
	public void createPaymentCustomer(@PathVariable Long customerId,
									  @RequestParam String currencyCode) throws StripeException
	{
		customerStripeAccountService.createCustomer(customerId, currencyCode);
	}

	@PostMapping("/payment-methods/add")
	public void addPaymentMethodToCustomer(@PathVariable long customerId,
										   @RequestParam String paymentMethodToken) throws StripeException
	{
		paymentMethodService.addPaymentMethodToCustomer(customerId, paymentMethodToken);
	}

	@GetMapping("/payment-methods")
	public List<CustomerPaymentMethod> retrieveCustomerPaymentMethods(@PathVariable long customerId) throws StripeException
	{
		return paymentMethodService.getPaymentMethodsForCustomer(customerId);
	}
}
