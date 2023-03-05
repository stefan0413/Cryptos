package com.cryptos.apigateway.controllers.trading;

import com.cryptos.apigateway.model.trading.OrderRequest;
import com.cryptos.apigateway.service.trading.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trading/{customerId}/orders")
public class OrderController
{

	private final OrderService orderService;

	public OrderController(OrderService orderService)
	{
		this.orderService = orderService;
	}

	@PostMapping
	public void makeOrder(@PathVariable long customerId,
						  @RequestBody OrderRequest orderRequest)
	{
		orderService.makeOrder(customerId, orderRequest);
	}
}
