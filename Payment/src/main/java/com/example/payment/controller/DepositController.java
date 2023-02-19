package com.example.payment.controller;

import com.example.payment.service.DepositService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/private/payments/payment-intents/{customerId}")
public class DepositController
{

	private final DepositService depositService;
	public DepositController(DepositService depositService)
	{
		this.depositService = depositService;
	}

	@PostMapping("/create")
	public String createPaymentIntent(@PathVariable long customerId,
									  @RequestParam(required = false) String paymentMethodId,
									  @RequestParam String currency,
									  @RequestParam BigDecimal amount) throws Exception
	{
		return depositService.createPaymentIntent(customerId, paymentMethodId, currency, amount);
	}

	@PostMapping("/confirm")
	public void confirmPaymentIntent(@PathVariable long customerId,
									   @RequestParam String paymentIntentId) throws Exception
	{
		depositService.confirmPaymentIntent(customerId, paymentIntentId);
	}
}
