package com.cryptos.trading.trading.controller;

import com.cryptos.trading.trading.model.OrderRequest;
import com.cryptos.trading.trading.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/trading/{customerId}/orders")
public class OrderController
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final OrderService orderService;

	public OrderController(OrderService orderService)
	{
		this.orderService = orderService;
	}

	@PostMapping
	public void makeOrder(@PathVariable long customerId,
						  @RequestBody OrderRequest orderRequest)
	{
		logger.info(String.format("New order request received for customerId {%d}: %s",customerId, orderRequest.toString()));
		orderService.makeOrder(customerId, orderRequest);
	}
}
