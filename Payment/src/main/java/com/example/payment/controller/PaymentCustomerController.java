package com.example.payment.controller;

import com.example.payment.service.PaymentCustomerService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments/customers/{customerId}")
public class PaymentCustomerController
{

	private final PaymentCustomerService paymentCustomerService;

	public PaymentCustomerController(PaymentCustomerService paymentCustomerService)
	{
		this.paymentCustomerService = paymentCustomerService;
	}

	@PostMapping("/create")
	public String createPaymentCustomer(@PathVariable Long customerId) throws StripeException
	{
		return paymentCustomerService.createCustomer(customerId);
	}

	@PostMapping("/payment-methods/add")
	public String addPaymentMethodToCustomer(@PathVariable long customerId,
										   @RequestParam String paymentMethodToken) throws StripeException
	{
		return paymentCustomerService.addPaymentMethodToCustomer(customerId, paymentMethodToken);
	}

	@GetMapping("/payment-methods")
	public String retrieveCustomerPaymentMethods(@PathVariable long customerId) throws StripeException
	{
		List<String> expandList = List.of("sources");
		Map<String, Object> retrieveParams = Map.of("expand", expandList);

		return Customer.retrieve(String.valueOf(customerId), retrieveParams, null).toJson();
	}

	@GetMapping
	public String retrieveCustomer(@PathVariable long customerId) throws StripeException
	{
		return Customer.retrieve(String.valueOf(customerId)).toJson();
	}
}
