package com.example.payment.service;

import com.example.payment.exception.PaymentsException;
import com.example.payment.rest.ExchangeRatesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService
{

	private final ExchangeRatesService exchangeRatesService;

	public CurrencyConversionService(ExchangeRatesService exchangeRatesService)
	{
		this.exchangeRatesService = exchangeRatesService;
	}

	public BigDecimal convertAmountFromToCurrency(BigDecimal amount, String from, String to)
	{
		if (from.equals(to))
		{
			return amount;
		}

		try
		{

			Map<String, BigDecimal> exchangeRates = exchangeRatesService.getExchangeRates(from).conversion_rates();
			return amount.multiply(exchangeRates.get(to));
		}
		catch (Exception ex)
		{
			throw new PaymentsException("CurrencyConversion exception", ex.getMessage());
		}
	}
}
