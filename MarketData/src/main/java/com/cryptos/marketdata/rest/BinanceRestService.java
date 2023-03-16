package com.cryptos.marketdata.rest;

import com.cryptos.marketdata.exception.BinanceException;
import com.cryptos.marketdata.model.BinancePriceResponse;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public class BinanceRestService
{

	private final String BINANCE_API_PRICE_URL;

	public BinanceRestService(@Value("${binance.api.price-url}") String binanceApiPriceUrl)
	{
		BINANCE_API_PRICE_URL = binanceApiPriceUrl;
	}

	public BigDecimal getPrice(String symbol) throws IOException
	{
		try
		{
			return extractPrice(executeRequest(symbol));
		}
		catch (Exception e)
		{
			throw new BinanceException("BinanceException", e.getMessage());
		}
	}

	private HttpResponse executeRequest(String symbol) throws IOException
	{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(BINANCE_API_PRICE_URL + symbol + "USDT");

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