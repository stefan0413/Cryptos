package com.example.payment.service;

import com.example.payment.rest.ExchangeRatesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService
{
	public static BigDecimal convertAmountFromToCurrency(BigDecimal amount, String from, String to)
	{
		if(from.equals(to)){
			return amount;
		}

		Map<String, BigDecimal> exchangeRates = ExchangeRatesService.getExchangeRates(from).conversion_rates();
		return amount.multiply(exchangeRates.get(to));
	}
}
