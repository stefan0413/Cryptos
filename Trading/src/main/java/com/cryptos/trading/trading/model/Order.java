package com.cryptos.trading.trading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Order(Long id,
					Long customerId,
					SupportedCurrency currency,
					OrderType type,
					BigDecimal orderSize,
					BigDecimal orderPrice,
					OrderStatus status,
					BigDecimal totalCost,
					LocalDateTime createdAt,
					LocalDateTime executedAt)
{

}
