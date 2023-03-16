package com.cryptos.marketdata.model;

import java.math.BigDecimal;

public class BinancePriceResponse {
	private BigDecimal price;

	public BinancePriceResponse(BigDecimal price)
	{
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}