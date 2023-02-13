package com.example.payment.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig
{

	private String stripeApiKey = "sk_test_51MH0dvCucZ6lucMqNVBvffVtbqwA8kucCFkzmNS421Q7nEGu05Xnn3CM5ChaaDBJO65FhdgQQJnFMmpeNZRXgAV600Ocp05xfs";

	@PostConstruct
	public void init()
	{
		Stripe.apiKey = stripeApiKey;
	}
}
