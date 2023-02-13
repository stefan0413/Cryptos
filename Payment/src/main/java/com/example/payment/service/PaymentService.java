package com.example.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService
{

	public static final String SUCCEEDED = "succeeded";

	public String createPaymentIntent(long customerId, String paymentMethodId, String currency, BigDecimal amount) throws Exception
	{
		Map<String, Object> params = new HashMap<>();
		params.put("amount", convertAmountToStripeAmount(amount));
		params.put("currency", currency);
		params.put("customer", customerId);
		params.put("payment_method", paymentMethodId != null ? paymentMethodId : getDefaultPaymentMethod(customerId));

		return PaymentIntent.create(params).getId();
	}

	public void confirmPaymentIntent(long customerId, String paymentIntentId) throws Exception
	{
		Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId).confirm(params);

		if(paymentIntent.getStatus().equals(SUCCEEDED)){
			updateCustomerBalance(customerId, convertStripeAmountToAmount(paymentIntent.getAmount()));
		}
	}

	private String getDefaultPaymentMethod(long customerId) throws StripeException
	{
		return Customer.retrieve(String.valueOf(customerId)).getDefaultSource();
	}

	private long convertAmountToStripeAmount(BigDecimal amount)
	{
		return amount.multiply(BigDecimal.valueOf(100)).longValue();
	}

	private BigDecimal convertStripeAmountToAmount(Long amount)
	{
		return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100), new MathContext(3));
	}

	private void updateCustomerBalance(long customerId, BigDecimal amount) throws StripeException
	{
		Customer customer = Customer.retrieve(String.valueOf(customerId));

		Map<String, Object> params = Map.of("balance",
											customer.getBalance() + convertAmountToStripeAmount(amount));

		customer.update(params);
	}
}
