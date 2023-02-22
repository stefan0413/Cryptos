package com.cryptos.apigateway.service.payments;

import com.cryptos.apigateway.model.payments.withdrawal.WithdrawalResponseWrapper;
import com.cryptos.apigateway.model.payments.withdrawal.WithdrawalStatus;
import com.cryptos.apigateway.rest.payments.WithdrawalRestService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawalService
{

	private final WithdrawalRestService withdrawalRestService;

	public WithdrawalService(WithdrawalRestService withdrawalRestService)
	{
		this.withdrawalRestService = withdrawalRestService;
	}

	public WithdrawalResponseWrapper getCustomerWithdrawals(long customerId)
	{
		return withdrawalRestService.getCustomerWithdrawals(customerId);
	}

	public WithdrawalResponseWrapper getCustomerPendingWithdrawals(long customerId)
	{
		return withdrawalRestService.getCustomerPendingWithdrawals(customerId);
	}

	public void createWithdrawalRequest(long customerId, BigDecimal amount, String iban)
	{
		withdrawalRestService.createWithdrawalRequest(customerId, amount, iban);
	}

	public void updateWithdrawalStatus(long customerId, long withdrawalId, WithdrawalStatus withdrawalStatus)
	{
		withdrawalRestService.updateWithdrawalStatus(customerId, withdrawalId, withdrawalStatus);
	}
}
