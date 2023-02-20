package com.example.payment.service;

import com.example.payment.model.CustomerStripeAccount;
import com.example.payment.model.DepositRequest;
import com.example.payment.model.DepositStatus;
import com.example.payment.repository.DepositRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class DepositService
{

	private static final String SYSTEM_CURRENCY_CODE = "BGN";
	private static final String SUCCEEDED = "succeeded";

	private final DepositRepository depositRepository;
	private final CustomerStripeAccountService customerStripeAccountService;

	public DepositService(DepositRepository depositRepository, CustomerStripeAccountService customerStripeAccountService)
	{
		this.depositRepository = depositRepository;
		this.customerStripeAccountService = customerStripeAccountService;
	}

	public String createPaymentIntent(long customerId, String paymentMethodId, String currency, BigDecimal amount) throws Exception
	{
		CustomerStripeAccount customer = getFullCustomer(customerId);

		String customerCurrencyCode = customer.currency().toUpperCase();
		BigDecimal amountInCustomerCurrency = CurrencyConversionService.convertAmountFromToCurrency(amount, currency, customerCurrencyCode);
		if (paymentMethodId == null)
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

	public void confirmPaymentIntent(long customerId, String paymentIntentId) throws Exception
	{
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId).confirm();

		if (paymentIntent.getStatus().equals(SUCCEEDED))
		{
			depositRepository.updateStatus(paymentIntentId, DepositStatus.EXECUTED.name());

			BigDecimal convertedAmountToSystemCurrency = CurrencyConversionService.convertAmountFromToCurrency(convertStripeAmountToAmount(paymentIntent.getAmount()),
																											   paymentIntent.getCurrency().toUpperCase(), SYSTEM_CURRENCY_CODE);
			updateCustomerBalanceInStripe(customerId, convertedAmountToSystemCurrency);
			updateCustomerBalance(getFullCustomer(customerId), convertedAmountToSystemCurrency);
		}
		else
		{
			depositRepository.updateStatus(paymentIntentId, DepositStatus.FAILED_EXECUTION.name());
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
		customerStripeAccountService.updateCustomerBalance(customer.customerId(), customer.freeBalance().add(amount), customer.investedBalance());
	}

	private void updateCustomerBalanceInStripe(long customerId, BigDecimal amount) throws StripeException
	{
		Customer customer = Customer.retrieve(String.valueOf(customerId));

		Map<String, Object> params = Map.of("balance",
											customer.getBalance() + convertAmountToStripeAmount(amount));

		customer.update(params);
	}
}
