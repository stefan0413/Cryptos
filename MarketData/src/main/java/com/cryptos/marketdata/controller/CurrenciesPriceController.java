package com.cryptos.marketdata.controller;

import com.cryptos.marketdata.rest.BinanceRestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/private/currencies")
public class CurrenciesPriceController
{

	private final BinanceRestService binanceRestService;

	public CurrenciesPriceController(BinanceRestService binanceRestService)
	{
		this.binanceRestService = binanceRestService;
	}

	@GetMapping("/price")
	public BigDecimal getPrice(@RequestParam String symbol) throws IOException
	{
		return binanceRestService.getPrice(symbol);
	}
}
