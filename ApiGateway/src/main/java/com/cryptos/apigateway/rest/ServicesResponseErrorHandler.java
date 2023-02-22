package com.cryptos.apigateway.rest;

import com.cryptos.apigateway.exceptions.CustomerException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ServicesResponseErrorHandler implements ResponseErrorHandler
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
		throw new CustomerException(response.getHeaders().getFirst("REASON"), responseBody);
	}
}
