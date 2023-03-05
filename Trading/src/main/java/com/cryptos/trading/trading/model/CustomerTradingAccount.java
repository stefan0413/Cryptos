package com.cryptos.trading.trading.model;

import java.math.BigDecimal;

public record CustomerTradingAccount(long id,
									 long customerId,
									 SupportedCurrency supportedCurrency,
									 BigDecimal currencyAmount)
{

}
