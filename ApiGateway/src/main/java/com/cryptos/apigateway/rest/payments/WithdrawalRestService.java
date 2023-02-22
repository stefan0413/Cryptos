package com.cryptos.apigateway.rest.payments;

import com.cryptos.apigateway.model.payments.withdrawal.WithdrawalResponseWrapper;
import com.cryptos.apigateway.model.payments.withdrawal.WithdrawalStatus;
import com.cryptos.apigateway.rest.ServicesResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class WithdrawalRestService
{

	private final String GET_CUSTOMER_WITHDRAWALS_URL;
	private final String GET_PENDING_WITHDRAWALS_URL;
	private final String CREATE_WITHDRAWAL_REQUEST_URL;
	private final String UPDATE_WITHDRAWAL_STATUS_URL;

	private final RestTemplate restTemplate = new RestTemplate();

	public WithdrawalRestService(@Value("${payments.withdrawal.get-customer-withdrawals}") String getCustomerWithdrawalsUrl,
								 @Value("${payments.withdrawal.get-pending-withdrawals-for-customer}") String getPendingWithdrawalsUrl,
								 @Value("${payments.withdrawal.create-withdrawal-request}") String createWithdrawalRequestUrl,
								 @Value("${payments.withdrawal.update-withdrawal-status}") String updateWithdrawalStatusUrl)
	{
		GET_CUSTOMER_WITHDRAWALS_URL = getCustomerWithdrawalsUrl;
		GET_PENDING_WITHDRAWALS_URL = getPendingWithdrawalsUrl;
		CREATE_WITHDRAWAL_REQUEST_URL = createWithdrawalRequestUrl;
		UPDATE_WITHDRAWAL_STATUS_URL = updateWithdrawalStatusUrl;
		this.restTemplate.setErrorHandler(new ServicesResponseErrorHandler());
	}

	public WithdrawalResponseWrapper getCustomerWithdrawals(long customerId)
	{
		return restTemplate.getForObject(GET_CUSTOMER_WITHDRAWALS_URL, WithdrawalResponseWrapper.class, customerId);
	}

	public WithdrawalResponseWrapper getCustomerPendingWithdrawals(long customerId)
	{
		return restTemplate.getForObject(GET_PENDING_WITHDRAWALS_URL, WithdrawalResponseWrapper.class, customerId);
	}

	public void createWithdrawalRequest(long customerId, BigDecimal amount, String iban)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(CREATE_WITHDRAWAL_REQUEST_URL)
														   .queryParam("amount", amount)
														   .queryParam("iban", iban);

		restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId)).toUriString(), null,
				Void.class);
	}

	public void updateWithdrawalStatus(long customerId, long withdrawalId, WithdrawalStatus withdrawalStatus)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(UPDATE_WITHDRAWAL_STATUS_URL)
														   .queryParam("withdrawalStatus", withdrawalStatus);

		restTemplate.postForObject(
				builder.buildAndExpand(Map.of("customerId", customerId,
											  "withdrawalId", withdrawalId)).toUriString(), null,
				Void.class);
	}
}
