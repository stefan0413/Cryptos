package com.example.payment.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig
{
	String stripeApiKey;

	public StripeConfig(@Value("${stripe.api-key}") String stripeApiKey)
	{
		this.stripeApiKey = stripeApiKey;
	}

	@PostConstruct
	public void init()
	{
		Stripe.apiKey = stripeApiKey;
	}
}
