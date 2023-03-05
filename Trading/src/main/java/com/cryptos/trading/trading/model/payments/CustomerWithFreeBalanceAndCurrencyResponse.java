package com.cryptos.trading.trading.model.payments;

import java.math.BigDecimal;

public record CustomerWithFreeBalanceAndCurrencyResponse(long customerId,
														 String currency,
														 BigDecimal freeBalance)
{

}

