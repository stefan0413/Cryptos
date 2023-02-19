package com.example.payment.rest;

import com.example.payment.model.CustomerDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRatesService
{

	private static final String url = "https://v6.exchangerate-api.com/v6/6385298c43a888c7956e7680/latest/{fromCurrency}";

	private static final RestTemplate restTemplate = new RestTemplate();

	public record ConversionRates(Map<String, BigDecimal> conversion_rates)
	{

	}

	public static ConversionRates getExchangeRates(String fromCurrency)
	{
		return restTemplate.getForObject(url, ConversionRates.class, fromCurrency);
	}
}
