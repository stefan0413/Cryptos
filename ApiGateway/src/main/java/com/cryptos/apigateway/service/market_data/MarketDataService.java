package com.cryptos.apigateway.service.market_data;

import com.cryptos.apigateway.rest.market_data.MarketDataRestService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MarketDataService
{

	private final MarketDataRestService marketDataRestService;

	public MarketDataService(MarketDataRestService marketDataRestService)
	{
		this.marketDataRestService = marketDataRestService;
	}

	public BigDecimal getPrice(String symbol)
	{
		return marketDataRestService.getPriceForCryptoCurrencyInUSDT(symbol);
	}
}
