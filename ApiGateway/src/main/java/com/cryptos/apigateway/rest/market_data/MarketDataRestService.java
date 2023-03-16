package com.cryptos.apigateway.rest.market_data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
public class MarketDataRestService
{
	private final String MARKET_DATA_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public MarketDataRestService(@Value("${market-data.currency-price-url}") String marketDataCurrencyPriceUrl)
	{
		MARKET_DATA_URL = marketDataCurrencyPriceUrl;
	}

	public BigDecimal getPriceForCryptoCurrencyInUSDT(String symbol)
	{

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(MARKET_DATA_URL)
														   .queryParam("symbol", symbol);

		return restTemplate.getForObject(builder.toUriString(), BigDecimal.class);
	}
}
