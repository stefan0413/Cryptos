package com.cryptos.trading.trading.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class MarketDataRestService
{

	private final String MARKET_DATA_URL = "http://localhost:8083/private/currencies/price";
	private final RestTemplate restTemplate = new RestTemplate();

	public BigDecimal getPriceForCryptoCurrencyInUSDT(String symbol)
	{

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(MARKET_DATA_URL)
														   .queryParam("symbol", symbol);

		return restTemplate.getForObject(builder.toUriString(), BigDecimal.class);
	}
}
