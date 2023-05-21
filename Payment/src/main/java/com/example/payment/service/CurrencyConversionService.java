package com.example.payment.service;

import com.example.payment.exception.CurrencyConversionException;
import com.example.payment.rest.ExchangeRatesRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService
{

	private final ExchangeRatesRestService exchangeRatesRestService;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CurrencyConversionService(ExchangeRatesRestService exchangeRatesRestService)
	{
		this.exchangeRatesRestService = exchangeRatesRestService;
	}

	public BigDecimal convertAmountFromToCurrency(BigDecimal amount, String from, String to)
	{
		logger.info("Getting exchange rate from: " + from + " to: " + to);
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
			throw new CurrencyConversionException(ex.getMessage());
		}
	}
}
