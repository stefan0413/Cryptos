package com.cryptos.apigateway.controllers.payments;

import com.cryptos.apigateway.model.payments.withdrawal.WithdrawalResponseWrapper;
import com.cryptos.apigateway.model.payments.withdrawal.WithdrawalStatus;
import com.cryptos.apigateway.service.payments.WithdrawalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payments/{customerId}/withdrawals")
public class WithdrawalController
{

	private final WithdrawalService withdrawalService;

	public WithdrawalController(WithdrawalService withdrawalService)
	{
		this.withdrawalService = withdrawalService;
	}

	@GetMapping
	public WithdrawalResponseWrapper getCustomerWithdrawals(@PathVariable long customerId)
	{
		return withdrawalService.getCustomerWithdrawals(customerId);
	}

	@GetMapping("/pending")
	public WithdrawalResponseWrapper getCustomerPendingWithdrawals(@PathVariable long customerId)
	{
		return withdrawalService.getCustomerPendingWithdrawals(customerId);
	}

	@PostMapping("/create")
	public void createWithdrawal(@PathVariable long customerId,
								 @RequestParam BigDecimal amount,
								 @RequestParam String iban)
	{
		withdrawalService.createWithdrawalRequest(customerId, amount, iban);
	}

	@PatchMapping("/withdrawal-statuses/{withdrawalId}")
	public void createWithdrawal(@PathVariable long customerId,
								 @PathVariable long withdrawalId,
								 @RequestParam WithdrawalStatus withdrawalStatus)
	{
		withdrawalService.updateWithdrawalStatus(customerId, withdrawalId, withdrawalStatus);
	}
}
