package com.example.payment.service;

import com.example.payment.model.CustomerDataResponse;
import com.example.payment.model.customer_stripe_account.CustomerWithFreeBalanceAndCurrencyResponse;
import com.example.payment.model.payment_method.CustomerPaymentMethod;
import com.example.payment.model.customer_stripe_account.CustomerStripeAccount;
import com.example.payment.model.customer_stripe_account.CustomerStripeAccountRequest;
import com.example.payment.repository.CustomerStripeAccountRepository;
import com.example.payment.rest.CustomerRestService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerStripeAccountService
{

	private final CustomerRestService customerRestService;
	private final CustomerStripeAccountRepository customerStripeAccountRepository;
	private final PaymentMethodService paymentMethodService;

	public CustomerStripeAccountService(CustomerRestService customerRestService, CustomerStripeAccountRepository customerStripeAccountRepository, PaymentMethodService paymentMethodService)
	{
		this.customerRestService = customerRestService;
		this.customerStripeAccountRepository = customerStripeAccountRepository;
		this.paymentMethodService = paymentMethodService;
	}

	public void createCustomer(long customerId, String currencyCode) throws StripeException
	{
		CustomerDataResponse customerData = customerRestService.getCustomerDataById(customerId);

		createStripeCustomer(customerData);
		saveCustomerStripeAccount(customerData, currencyCode);
	}

	public CustomerStripeAccount getFullCustomer(long customerId)
	{
		CustomerStripeAccount customerStripeAccount =
				customerStripeAccountRepository.getCustomerStripeAccountByCustomerId(customerId);

		List<CustomerPaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsForCustomer(customerId).customerPaymentMethods();


		return buildFullCustomerStripeAccount(customerStripeAccount, paymentMethods);
	}

	public void updateCustomerBalance(long customerId, BigDecimal freeBalance)
	{
		customerStripeAccountRepository.updateCustomerBalance(customerId, freeBalance);
	}
	public CustomerWithFreeBalanceAndCurrencyResponse getCustomerFreeBalance(long customerId)
	{
		return customerStripeAccountRepository.getCustomerFreeBalance(customerId);
	}

	private CustomerStripeAccount buildFullCustomerStripeAccount(CustomerStripeAccount customer,
																 List<CustomerPaymentMethod> paymentMethods)
	{
		return new CustomerStripeAccount(customer.id(),
										 customer.customerId(),
										 customer.currency(),
										 customer.freeBalance(),
										 customer.email(),
										 customer.names(),
										 paymentMethods);
	}

	private void createStripeCustomer(CustomerDataResponse customerData) throws StripeException
	{
		Map<String, Object> customerParameters = new HashMap<>();
		customerParameters.put("id", customerData.customerId());
		customerParameters.put("email", customerData.email());
		customerParameters.put("name", getFullName(customerData).trim());

		Customer.create(customerParameters);
	}

	private void saveCustomerStripeAccount(CustomerDataResponse customerData, String currencyCode)
	{
		CustomerStripeAccountRequest customerStripeAccountRequest =
				new CustomerStripeAccountRequest(customerData.customerId(),
												 currencyCode,
												 BigDecimal.ZERO,
												 customerData.email(),
												 getFullName(customerData).trim());

		customerStripeAccountRepository.save(customerStripeAccountRequest);
	}

	private static String getFullName(CustomerDataResponse customerDataResponse)
	{
		return removeNullNames(customerDataResponse.firstName()) +
			   removeNullNames(customerDataResponse.secondName()) +
			   removeNullNames(customerDataResponse.lastName());
	}

	private static String removeNullNames(String name)
	{
		return name != null ? (name + " ") : "";
	}
}
