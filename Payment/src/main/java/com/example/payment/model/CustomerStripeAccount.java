package com.example.payment.model;

import java.math.BigDecimal;
import java.util.List;

public record CustomerStripeAccount(Long id,
									Long customerId,
									String currency,
									BigDecimal freeBalance,
									BigDecimal investedBalance,
									String email,
									String names,
									List<CustomerPaymentMethod> paymentMethods)
{

}
