package com.example.payment.controller;

import com.example.payment.model.withdrawal.WithdrawalResponseWrapper;
import com.example.payment.model.withdrawal.WithdrawalStatus;
import com.example.payment.service.WithdrawalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/private/payments/withdrawals/{customerId}")
public class WithdrawalController
{

	private final WithdrawalService withdrawalService;

	public WithdrawalController(WithdrawalService withdrawalService)
	{
		this.withdrawalService = withdrawalService;
	}

	@GetMapping
	public WithdrawalResponseWrapper getWithdrawalsForCustomer(@PathVariable long customerId)
	{
		return withdrawalService.getWithdrawalsForCustomer(customerId);
	}

	@GetMapping("/pending")
	public WithdrawalResponseWrapper getPendingWithdrawalsForCustomer(@PathVariable long customerId)
	{
		return withdrawalService.getPendingWithdrawalsForCustomer(customerId);
	}

	@PostMapping("/create")
	public void createWithdrawalRequest(@PathVariable long customerId,
										@RequestParam BigDecimal amount,
										@RequestParam String iban)
	{
		withdrawalService.createWithdrawal(customerId, iban, amount);
	}

	@PostMapping("/withdrawal-statuses/{withdrawalId}")
	public void updateWithdrawalStatus(@PathVariable long customerId,
									   @PathVariable long withdrawalId,
									   WithdrawalStatus withdrawalStatus)
	{
		withdrawalService.updateWithdrawalStatus(withdrawalId, withdrawalStatus);
	}
}
