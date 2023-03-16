package com.cryptos.apigateway.model.payments;

import java.math.BigDecimal;
import java.util.List;

public record CustomerStripeAccount(Long id,
									Long customerId,
									String currency,
									BigDecimal freeBalance,
									String email,
									String names,
									List<CustomerPaymentMethod> paymentMethods)
{

}
