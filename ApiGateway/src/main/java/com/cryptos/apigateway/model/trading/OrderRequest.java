package com.cryptos.apigateway.model.trading;

import java.math.BigDecimal;

public record OrderRequest(String orderType,
						   String supportedCurrency,
						   BigDecimal orderSize)
{

}
