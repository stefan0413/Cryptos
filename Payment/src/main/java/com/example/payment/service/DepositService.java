package com.example.payment.service;

import com.example.payment.model.customer_stripe_account.CustomerStripeAccount;
import com.example.payment.model.deposit.Deposit;
import com.example.payment.model.deposit.DepositRequest;
import com.example.payment.model.deposit.DepositResponseWrapper;
import com.example.payment.model.deposit.DepositStatus;
import com.example.payment.repository.DepositRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DepositService
{

	private static final String SYSTEM_CURRENCY_CODE = "BGN";
	private static final String SUCCEEDED = "succeeded";

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final DepositRepository depositRepository;
	private final CustomerStripeAccountService customerStripeAccountService;
	private final CurrencyConversionService currencyConversionService;

	public DepositService(DepositRepository depositRepository, CustomerStripeAccountService customerStripeAccountService, CurrencyConversionService currencyConversionService)
	{
		this.depositRepository = depositRepository;
		this.customerStripeAccountService = customerStripeAccountService;
		this.currencyConversionService = currencyConversionService;
	}

	public String createPaymentIntent(long customerId, String paymentMethodId, String currency, BigDecimal amount) throws Exception
	{
		CustomerStripeAccount customer = getFullCustomer(customerId);

		String customerCurrencyCode = customer.currency().toUpperCase();
		BigDecimal amountInCustomerCurrency = currencyConversionService.convertAmountFromToCurrency(amount, currency, customerCurrencyCode);
		if (paymentMethodId == null || paymentMethodId.equals(""))
		{
			paymentMethodId = getDefaultPaymentMethod(customerId);
		}

		String paymentIntentId = PaymentIntent.create(PaymentIntentCreateParams.builder()
																			   .setAmount(convertAmountToStripeAmount(amountInCustomerCurrency))
																			   .setCurrency(customerCurrencyCode)
																			   .setCustomer(String.valueOf(customerId))
																			   .setPaymentMethod(paymentMethodId)
																			   .build()).getId();

		depositRepository.save(new DepositRequest(customerId, paymentMethodId, paymentIntentId, amountInCustomerCurrency, customerCurrencyCode, DepositStatus.PENDING, LocalDateTime.now()));

		return paymentIntentId;
	}

	public DepositResponseWrapper getCustomerDeposits(long customerId)
	{
		return new DepositResponseWrapper(depositRepository.getAllDepositsForCustomer(customerId));
	}

	public void confirmPaymentIntent(long customerId, String paymentIntentId) throws StripeException
	{
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

		if(!paymentIntent.getCustomer().equals(String.valueOf(customerId))){
			throw new IllegalArgumentException("Payment intent does not belong to customer");
		}

		updateCustomerAndDepositData(customerId, paymentIntent.confirm());
	}

	private void updateCustomerAndDepositData(long customerId, PaymentIntent paymentIntent) throws StripeException
	{
		System.out.println("PaymentIntentStatus: " + paymentIntent.getStatus());
		if (paymentIntent.getStatus().equals(SUCCEEDED))
		{
			depositRepository.updateStatus(paymentIntent.getId(), DepositStatus.EXECUTED.name());
			depositRepository.updateExecutionTimestamp(paymentIntent.getId(), LocalDateTime.now());

			BigDecimal convertedAmountToSystemCurrency = currencyConversionService.convertAmountFromToCurrency(convertStripeAmountToAmount(paymentIntent.getAmount()),
																											   paymentIntent.getCurrency().toUpperCase(), SYSTEM_CURRENCY_CODE);
			updateCustomerBalanceInStripe(customerId, convertedAmountToSystemCurrency);
			updateCustomerBalance(getFullCustomer(customerId), convertStripeAmountToAmount(paymentIntent.getAmount()));
		}
		else
		{
			depositRepository.updateStatus(paymentIntent.getId(), DepositStatus.FAILED_EXECUTION.name());
		}
	}

	private CustomerStripeAccount getFullCustomer(long customerId)
	{
		return customerStripeAccountService.getFullCustomer(customerId);
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
		return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
	}

	private void updateCustomerBalance(CustomerStripeAccount customer, BigDecimal amount)
	{
		customerStripeAccountService.updateCustomerBalance(customer.customerId(), customer.freeBalance().add(amount));
	}

	private void updateCustomerBalanceInStripe(long customerId, BigDecimal amount) throws StripeException
	{
		Customer customer = Customer.retrieve(String.valueOf(customerId));

		Map<String, Object> params = Map.of("balance",
											customer.getBalance() + convertAmountToStripeAmount(amount));

		customer.update(params);
	}
}
