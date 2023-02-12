package com.example.payment.controller;

import com.example.payment.service.PaymentCustomerService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/customers")
public class PaymentCustomerController
{
	private final PaymentCustomerService paymentCustomerService;

	public PaymentCustomerController(PaymentCustomerService paymentCustomerService)
	{
		this.paymentCustomerService = paymentCustomerService;
	}

	@PostMapping("/create/{customerId}")
	public String createPaymentCustomer(@PathVariable Long customerId,
										@RequestParam String email,
										@RequestParam String currency
										) throws StripeException
	{
		return paymentCustomerService.createCustomer(customerId, currency, email);
	}

}
