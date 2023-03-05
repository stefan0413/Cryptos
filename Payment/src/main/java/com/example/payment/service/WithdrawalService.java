package com.example.payment.service;

import com.example.payment.model.customer_stripe_account.CustomerStripeAccount;
import com.example.payment.model.withdrawal.Withdrawal;
import com.example.payment.model.withdrawal.WithdrawalRequest;
import com.example.payment.model.withdrawal.WithdrawalResponseWrapper;
import com.example.payment.model.withdrawal.WithdrawalStatus;
import com.example.payment.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WithdrawalService
{

	private final CustomerStripeAccountService customerStripeAccountService;
	private final WithdrawalRepository withdrawalRepository;

	public WithdrawalService(CustomerStripeAccountService customerStripeAccountService, WithdrawalRepository withdrawalRepository)
	{
		this.customerStripeAccountService = customerStripeAccountService;
		this.withdrawalRepository = withdrawalRepository;
	}

	public void createWithdrawal(long customerId, String iban, BigDecimal amount)
	{
		CustomerStripeAccount customer = customerStripeAccountService.getFullCustomer(customerId);

		if (customer.freeBalance().compareTo(amount) < 0)
		{
			throw new RuntimeException("the withdrawal amount is greater than the free funds");
		}

		customerStripeAccountService.updateCustomerBalance(customerId, customer.freeBalance().subtract(amount));
		System.out.println("Amount: " + amount);
		withdrawalRepository.saveWithdrawalRequest(new WithdrawalRequest(customerId, amount, iban, WithdrawalStatus.PENDING, LocalDateTime.now()));
	}

	public WithdrawalResponseWrapper getWithdrawalsForCustomer(long customerId)
	{
		return new WithdrawalResponseWrapper(withdrawalRepository.getWithdrawalsForCustomer(customerId));
	}

	public WithdrawalResponseWrapper getPendingWithdrawalsForCustomer(long customerId)
	{
		return new WithdrawalResponseWrapper(withdrawalRepository.getPendingWithdrawalsForCustomer(customerId));
	}

	public void updateWithdrawalStatus(long id, WithdrawalStatus status)
	{
		withdrawalRepository.updateStatus(id, status);
	}
}
