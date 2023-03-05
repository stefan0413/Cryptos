package com.cryptos.trading.trading.service;

import com.cryptos.trading.trading.exception.NotEnoughFundsException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderValidationService
{

	private static final String ORDER_DEFAULT_CURRENCY = "USD";

	private final CurrencyConversionService currencyConversionService;

	public OrderValidationService(CurrencyConversionService currencyConversionService)
	{
		this.currencyConversionService = currencyConversionService;
	}

	public void validateBuyOrder(String customerCurrency,
								 BigDecimal customerFreeBalanceInCustomerCurrency,
								 BigDecimal orderTotalCost)
	{
		if (customerFreeBalanceInCustomerCurrency.compareTo(currencyConversionService.convertAmountFromToCurrency(orderTotalCost, ORDER_DEFAULT_CURRENCY, customerCurrency)) < 0)
		{
			throw new NotEnoughFundsException("Not enough balance to execute order");
		}
	}
}
