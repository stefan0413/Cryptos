package com.cryptos.marketdata.rest;

import com.cryptos.marketdata.model.BinancePriceResponse;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class BinanceRestClient
{

	private static final String BINANCE_API_PRICE_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";

	public BigDecimal getPrice(String symbol) throws IOException
	{
		return extractPrice(executeRequest(symbol));
	}

	private HttpResponse executeRequest(String symbol) throws IOException
	{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(BINANCE_API_PRICE_URL + symbol);

		return httpClient.execute(request);

	}

	private BigDecimal extractPrice(HttpResponse response) throws IOException
	{
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity);

		BinancePriceResponse binancePriceResponse = new Gson().fromJson(json, BinancePriceResponse.class);

		return binancePriceResponse.getPrice();
	}
}