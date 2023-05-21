package com.example.payment.service;

import com.example.payment.model.payment_method.CustomerPaymentMethodResponseWrapper;
import com.example.payment.repository.PaymentMethodRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentMethodService
{

	private final PaymentMethodRepository paymentMethodRepository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public PaymentMethodService(PaymentMethodRepository paymentMethodRepository)
	{
		this.paymentMethodRepository = paymentMethodRepository;
	}

	public void addPaymentMethodToCustomer(long customerId, String methodToken) throws StripeException
	{
		logger.info(String.format("Adding payment method to customer: customerId=%d, methodToken=%s", customerId, methodToken));
		
		PaymentSource paymentSource = addPaymentMethodToStripe(customerId, methodToken);
		savePaymentMethod(customerId, methodToken, paymentSource.getId());
	}

	public void savePaymentMethod(long customerId, String methodToken, String stripePaymentMethodId)
	{
		paymentMethodRepository.save(customerId, methodToken, stripePaymentMethodId);
	}

	public PaymentSource addPaymentMethodToStripe(long customerId, String methodToken) throws StripeException
	{
		List<String> expandList = List.of("sources");
		Map<String, Object> retrieveParams = Map.of("expand", expandList);

		Customer customer = Customer.retrieve(String.valueOf(customerId), retrieveParams, null);

		Map<String, Object> params = Map.of("source", methodToken);

		return customer.getSources().create(params);
	}

	public CustomerPaymentMethodResponseWrapper getPaymentMethodsForCustomer(long customerId)
	{
		return new CustomerPaymentMethodResponseWrapper(paymentMethodRepository.getPaymentMethodsForCustomer(customerId));
	}
}
