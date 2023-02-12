package com.example.payment.service;

import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService
{

	public String createPaymentIntent(Integer amount, String currency) throws Exception
	{
		Map<String, Object> params = new HashMap<>();
		params.put("amount", amount);
		params.put("currency", currency);

		return PaymentIntent.create(params).toJson();
	}

	public String confirmPaymentIntent(String paymentIntentId) throws Exception
	{
		Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		return PaymentIntent.retrieve(paymentIntentId).confirm(params).toJson();

	}
}
