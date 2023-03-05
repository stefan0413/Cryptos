package com.cryptos.apigateway.service.trading;

import com.cryptos.apigateway.model.trading.OrderRequest;
import com.cryptos.apigateway.rest.trading.OrderRestService;
import org.springframework.stereotype.Service;

@Service
public class OrderService
{
	private final OrderRestService orderRestService;

	public OrderService(OrderRestService orderRestService)
	{
		this.orderRestService = orderRestService;
	}
	public void makeOrder(long customerId, OrderRequest orderRequest)
	{
		orderRestService.makeOrder(customerId, orderRequest);
	}
}
