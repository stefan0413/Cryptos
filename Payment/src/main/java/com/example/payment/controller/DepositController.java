package com.example.payment.controller;

import com.example.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/payments/{customerId}/payment-intents")
public class DepositController
{

	private final PaymentService paymentService;

	public DepositController(PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	@PostMapping("/create")
	public String createPaymentIntent(@PathVariable long customerId,
									  @RequestParam(required = false) String paymentMethodId,
									  @RequestParam String currency,
									  @RequestParam BigDecimal amount) throws Exception
	{
		return paymentService.createPaymentIntent(customerId, paymentMethodId, currency, amount);
	}

	@PostMapping("/confirm")
	public void confirmPaymentIntent(@PathVariable long customerId,
									   @RequestParam String paymentIntentId) throws Exception
	{
		paymentService.confirmPaymentIntent(customerId, paymentIntentId);
	}
}
