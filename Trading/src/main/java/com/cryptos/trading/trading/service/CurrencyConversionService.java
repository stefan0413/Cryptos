package com.cryptos.trading.trading.service;

import com.cryptos.trading.trading.exception.PaymentsException;
import com.cryptos.trading.trading.rest.ExchangeRatesRestService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService
{

	private final ExchangeRatesRestService exchangeRatesService;

	public CurrencyConversionService(ExchangeRatesRestService exchangeRatesService)
	{
		this.exchangeRatesService = exchangeRatesService;
	}

	public BigDecimal convertAmountFromToCurrency(BigDecimal amount, String from, String to)
	{
		if (from.equalsIgnoreCase(to))
		{
			return amount;
		}

		try
		{

			Map<String, BigDecimal> exchangeRates = exchangeRatesService.getExchangeRates(from.toUpperCase()).conversion_rates();
			return amount.multiply(exchangeRates.get(to.toUpperCase()));
		}
		catch (Exception ex)
		{
			throw new PaymentsException("CurrencyConversion exception", ex.getMessage());
		}
	}
}
