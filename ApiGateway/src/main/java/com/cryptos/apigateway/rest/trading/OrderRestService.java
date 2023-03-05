package com.cryptos.apigateway.rest.trading;

import com.cryptos.apigateway.model.trading.OrderRequest;
import com.cryptos.apigateway.rest.ServicesResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderRestService
{

	private final String MAKE_ORDER_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public OrderRestService(@Value("${trading.make-order-url}") String makeOrderUrl)
	{
		MAKE_ORDER_URL = makeOrderUrl;
		restTemplate.setErrorHandler(new ServicesResponseErrorHandler());
	}


	public void makeOrder(long customerId, OrderRequest orderRequest)
	{
		restTemplate.postForObject(MAKE_ORDER_URL, orderRequest, Void.class, customerId);
	}
}
