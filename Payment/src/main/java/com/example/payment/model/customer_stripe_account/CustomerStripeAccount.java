package com.example.payment.model.customer_stripe_account;

import com.example.payment.model.payment_method.CustomerPaymentMethod;

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
