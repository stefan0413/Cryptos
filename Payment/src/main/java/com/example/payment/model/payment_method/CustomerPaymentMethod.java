package com.example.payment.model.payment_method;

public record CustomerPaymentMethod(Long id,
									Long customerId,
									String token,
									String stripePaymentMethodId)
{

}
