package com.example.payment.controller;

import com.example.payment.model.deposit.Deposit;
import com.example.payment.model.deposit.DepositResponseWrapper;
import com.example.payment.service.DepositService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/private/payments/payment-intents/{customerId}")
public class DepositController
{

	private final DepositService depositService;

	public DepositController(DepositService depositService)
	{
		this.depositService = depositService;
	}

	@GetMapping
	public DepositResponseWrapper getAllDepositsForCustomer(@PathVariable long customerId)
	{
		return depositService.getCustomerDeposits(customerId);
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
									 @RequestParam String paymentIntentId) throws StripeException
	{
		depositService.confirmPaymentIntent(customerId, paymentIntentId);
	}
}
