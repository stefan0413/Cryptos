package com.example.payment.service;

import com.example.payment.exception.PaymentsException;
import com.example.payment.rest.ExchangeRatesRestService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService
{

	private final ExchangeRatesRestService exchangeRatesRestService;

	public CurrencyConversionService(ExchangeRatesRestService exchangeRatesRestService)
	{
		this.exchangeRatesRestService = exchangeRatesRestService;
	}

	public BigDecimal convertAmountFromToCurrency(BigDecimal amount, String from, String to)
	{
		if (from.equals(to))
		{
			return amount;
		}

		try
		{

			Map<String, BigDecimal> exchangeRates = exchangeRatesRestService.getExchangeRates(from).conversion_rates();
			return amount.multiply(exchangeRates.get(to));
		}
		catch (Exception ex)
		{
			throw new PaymentsException("CurrencyConversion exception", ex.getMessage());
		}
	}
}
