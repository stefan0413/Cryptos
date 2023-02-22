package com.cryptos.apigateway.model.payments;

public record CustomerPaymentMethod(Long id,
									Long customerId,
									String token,
									String stripePaymentMethodId)
{

}
