package com.cryptos.apigateway.rest.payments;

import com.cryptos.apigateway.model.payments.deposit.DepositResponseWrapper;
import com.cryptos.apigateway.rest.ServicesResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class DepositRestService
{

	private final String GET_DEPOSITS_FOR_CUSTOMER_URL;
	private final String CREATE_DEPOSIT;
	private final String CONFIRM_DEPOSIT;

	private final RestTemplate restTemplate = new RestTemplate();

	public DepositRestService(@Value("${payments.deposit.get-deposits-for-customer}") String getDepositsForCustomer,
							  @Value("${payments.deposit.create-payment-intent}") String createDeposit,
							  @Value("${payments.deposit.confirm-payment-intent}") String confirmDeposit)
	{
		GET_DEPOSITS_FOR_CUSTOMER_URL = getDepositsForCustomer;
		CREATE_DEPOSIT = createDeposit;
		CONFIRM_DEPOSIT = confirmDeposit;

		restTemplate.setErrorHandler(new ServicesResponseErrorHandler());
	}

	public DepositResponseWrapper getCustomerDeposits(long customerId)
	{
		return restTemplate.getForObject(GET_DEPOSITS_FOR_CUSTOMER_URL, DepositResponseWrapper.class, customerId);
	}

	public String createDeposit(long customerId, String paymentMethodId, String currency, BigDecimal amount)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(CREATE_DEPOSIT)
														   .queryParam("paymentMethodId", paymentMethodId)
														   .queryParam("currency", currency)
														   .queryParam("amount", amount);

		return restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId)).toUriString(), null,
				String.class);
	}

	public void confirmDeposit(long customerId, String paymentIntentId)
	{

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(CONFIRM_DEPOSIT)
														   .queryParam("paymentIntentId", paymentIntentId);

		restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId)).toUriString(), null,
				Void.class);
	}
}
