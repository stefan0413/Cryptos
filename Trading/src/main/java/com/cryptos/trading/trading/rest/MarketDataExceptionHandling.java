package com.cryptos.trading.trading.rest;

import com.cryptos.trading.trading.exception.CurrencyConversionException;
import com.cryptos.trading.trading.exception.MarketDataException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.MarshalException;
import java.util.stream.Collectors;

public class MarketDataExceptionHandling implements ResponseErrorHandler
{

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException
	{
		return (response.getStatusCode().isError());
	}

	@Override public void handleError(ClientHttpResponse response) throws IOException
	{
		String responseBody = new BufferedReader(new InputStreamReader(response.getBody()))
				.lines().collect(Collectors.joining("\n"));

		throw new MarketDataException("MarketDataException", responseBody);
	}
}