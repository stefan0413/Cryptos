package com.cryptos.trading.trading.rest;

import com.cryptos.trading.trading.model.payments.CustomerWithFreeBalanceAndCurrencyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentsRestService
{

	private final String MODIFY_CUSTOMER_FREE_BALANCE_URL;
	private final String GET_CUSTOMER_WITH_FREE_BALANCE_AND_CURRENCY_URL;

	private final RestTemplate restTemplate = new RestTemplate();

	public PaymentsRestService(@Value("${payments.invest-funds-url}") String modifyCustomerFreeBalanceUrl,
							   @Value("${payments.customer-with-free-balance-and-currency-url}") String getCustomerWithFreeBalanceAndCurrencyUrl)
	{
		MODIFY_CUSTOMER_FREE_BALANCE_URL = modifyCustomerFreeBalanceUrl;
		GET_CUSTOMER_WITH_FREE_BALANCE_AND_CURRENCY_URL = getCustomerWithFreeBalanceAndCurrencyUrl;
		restTemplate.setErrorHandler(new PaymentsResponseErrorHandler());
	}

	public CustomerWithFreeBalanceAndCurrencyResponse getCustomerWithFreeBalanceAndCurrency(long customerId)
	{
		return restTemplate.getForObject(
				UriComponentsBuilder.fromUriString(GET_CUSTOMER_WITH_FREE_BALANCE_AND_CURRENCY_URL)
									.buildAndExpand(Map.of("customerId", customerId))
									.toUriString(), CustomerWithFreeBalanceAndCurrencyResponse.class);
	}

	public void modifyCustomerFreeBalance(long customerId, BigDecimal fundsForInvestment)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(MODIFY_CUSTOMER_FREE_BALANCE_URL)
														   .queryParam("fundsForInvestment", fundsForInvestment);

		restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId)).toUriString(), null,
				Void.class);
	}
}
