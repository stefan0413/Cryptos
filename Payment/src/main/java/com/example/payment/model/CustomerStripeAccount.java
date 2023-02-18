package com.example.payment.model;

import java.math.BigDecimal;

public record CustomerStripeAccount(Long id,
									Long customerId,
									BigDecimal freeBalance,
									BigDecimal investedBalance,
									String email,
									String names)
{

}
