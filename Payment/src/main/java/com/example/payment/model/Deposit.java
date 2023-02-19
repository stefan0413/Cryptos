package com.example.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Deposit(Long id,
					  Long customerId,
					  String stripePaymentMethodId,
					  String stripePaymentIntentId,
					  BigDecimal amount,
					  String currencyCode,
					  DepositStatus depositStatus,
					  LocalDateTime createdAt,
					  LocalDateTime executedAt)
{

}
