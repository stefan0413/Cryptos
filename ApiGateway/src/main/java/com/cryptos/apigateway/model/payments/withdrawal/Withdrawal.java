package com.cryptos.apigateway.model.payments.withdrawal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Withdrawal (Long id,
					      Long customerId,
						  BigDecimal amount,
						  String iban,
						  WithdrawalStatus status,
						  LocalDateTime createdAt)
{

}
