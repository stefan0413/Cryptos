package com.example.payment.model.customer_stripe_account;

import java.math.BigDecimal;

public record CustomerStripeAccountRequest(Long customerId,
										   String currencyCode,
										   BigDecimal freeBalance,
										   BigDecimal investedBalance,
										   String email,
										   String names)
{

}
