package com.example.payment.model.deposit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositRequest (Long customerId,
							  String stripePaymentMethodId,
							  String stripePaymentIntentId,
							  BigDecimal amount,
							  String currencyCode,
							  DepositStatus status,
							  LocalDateTime created_at)
{

}
