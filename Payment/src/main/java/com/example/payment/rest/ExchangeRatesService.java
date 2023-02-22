package com.example.payment.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExchangeRatesService
{

	private final String EXCHANGE_RATES_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public ExchangeRatesService(@Value("${currency-conversion.exchange-rates-url}") String exchangeRatesUrl)
	{
		EXCHANGE_RATES_URL = exchangeRatesUrl;
		restTemplate.setErrorHandler(new CurrencyConversionExceptionHandling());
	}

	public record ConversionRates(Map<String, BigDecimal> conversion_rates)
	{

	}

	public ConversionRates getExchangeRates(String fromCurrency)
	{
		return restTemplate.getForObject(EXCHANGE_RATES_URL, ConversionRates.class, fromCurrency);
	}
}
