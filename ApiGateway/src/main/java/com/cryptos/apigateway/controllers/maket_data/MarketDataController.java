package com.cryptos.apigateway.controllers.maket_data;

import com.cryptos.apigateway.service.market_data.MarketDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/market-data/crypto-currencies")
public class MarketDataController
{

	private final MarketDataService marketDataService;

	public MarketDataController(MarketDataService marketDataService)
	{
		this.marketDataService = marketDataService;
	}

	@GetMapping("/price")
	public BigDecimal getPrice(@RequestParam String symbol)
	{
		return marketDataService.getPrice(symbol);
	}
}
