package com.example.payment.model.customer_stripe_account;

import java.math.BigDecimal;

public record CustomerWithFreeBalanceAndCurrencyResponse(long customerId,
														 String currency,
														 BigDecimal freeBalance)
{

}
