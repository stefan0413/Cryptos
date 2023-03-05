package com.cryptos.trading.trading.model;

import java.math.BigDecimal;

public record OrderRequest(OrderType orderType,
						   SupportedCurrency supportedCurrency,
						   BigDecimal orderSize)
{

}
