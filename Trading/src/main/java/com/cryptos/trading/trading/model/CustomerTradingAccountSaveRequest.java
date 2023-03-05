package com.cryptos.trading.trading.model;

import java.math.BigDecimal;

public record CustomerTradingAccountSaveRequest(long customerId,
												SupportedCurrency supportedCurrency,
												BigDecimal currencyAmount)
{

}
