package com.cryptos.apigateway.controllers.payments;

import com.cryptos.apigateway.model.payments.deposit.DepositResponseWrapper;
import com.cryptos.apigateway.service.payments.DepositService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payments/{customerId}/deposits")
public class DepositController
{

	private final DepositService depositService;

	public DepositController(DepositService depositService)
	{
		this.depositService = depositService;
	}

	@GetMapping
	public DepositResponseWrapper getCustomerDeposits(@PathVariable long customerId)
	{
		return depositService.getCustomerDeposits(customerId);
	}

	@PostMapping("/create")
	public String createDeposit(@PathVariable long customerId,
							  @RequestParam(required = false) String paymentMethodId,
							  @RequestParam String currency,
							  @RequestParam BigDecimal amount)
	{
		return depositService.createDeposit(customerId, paymentMethodId, currency, amount);
	}

	@PostMapping("/confirm")
	public void confirmPaymentIntent(@PathVariable long customerId,
									 @RequestParam String paymentIntentId)
	{
		depositService.confirmDeposit(customerId, paymentIntentId);
	}
}
