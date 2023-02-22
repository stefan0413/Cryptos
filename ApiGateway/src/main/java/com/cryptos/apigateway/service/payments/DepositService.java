package com.cryptos.apigateway.service.payments;

import com.cryptos.apigateway.model.payments.deposit.DepositResponseWrapper;
import com.cryptos.apigateway.rest.payments.DepositRestService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepositService
{

	private final DepositRestService depositRestService;


	public DepositService(DepositRestService depositRestService)
	{
		this.depositRestService = depositRestService;
	}

	public DepositResponseWrapper getCustomerDeposits(long customerId)
	{
		return depositRestService.getCustomerDeposits(customerId);
	}

	public String createDeposit(long customerId, String paymentMethodId, String currency, BigDecimal amount)
	{
		return depositRestService.createDeposit(customerId, paymentMethodId, currency, amount);
	}

	public void confirmDeposit(long customerId, String paymentIntentId)
	{
		depositRestService.confirmDeposit(customerId, paymentIntentId);
	}
}
