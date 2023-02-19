package com.example.payment.model;

public record CustomerPaymentMethod(Long id,
									Long customerId,
									String token,
									String stripePaymentMethodId)
{

}
