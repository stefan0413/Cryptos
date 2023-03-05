package com.cryptos.trading.trading.service;

import com.cryptos.trading.trading.exception.NotEnoughFundsException;
import com.cryptos.trading.trading.model.CustomerTradingAccount;
import com.cryptos.trading.trading.model.CustomerTradingAccountSaveRequest;
import com.cryptos.trading.trading.model.OrderRequest;
import com.cryptos.trading.trading.model.SupportedCurrency;
import com.cryptos.trading.trading.repository.CustomerTradingAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerTradingAccountService
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerTradingAccountRepository customerTradingAccountRepository;

	public CustomerTradingAccountService(CustomerTradingAccountRepository customerTradingAccountRepository)
	{
		this.customerTradingAccountRepository = customerTradingAccountRepository;
	}

	public long saveCustomerTradingAccount(CustomerTradingAccountSaveRequest customerTradingAccountSaveRequest)
	{
		return customerTradingAccountRepository.save(customerTradingAccountSaveRequest);
	}

	public CustomerTradingAccount getCustomerTradingAccountByCustomerId(long customerId)
	{
		return customerTradingAccountRepository.getCustomerTradingAccountsByCustomerId(customerId)
											   .stream().findFirst().orElseThrow(() -> new NoSuchElementException("Customer trading account not found"));
	}

	public CustomerTradingAccount getCustomerTradingAccountByCustomerIdAndCurrency(long customerId, SupportedCurrency currency)
	{
		return customerTradingAccountRepository.getCustomerTradingAccountsByCustomerIdAndCurrency(customerId, currency)
											   .orElseThrow(() -> new NoSuchElementException("Customer trading account not found"));
	}

	public void updateCurrencyAmount(long customerId, SupportedCurrency currency, BigDecimal currencyAmount)
	{
		customerTradingAccountRepository.updateCurrencyAmount(customerId, currency, currencyAmount);
	}

	public void updateCustomerAssets(long customerId, OrderRequest orderRequest)
	{
		Optional<CustomerTradingAccount> customerTradingAccount = customerTradingAccountRepository.getCustomerTradingAccountsByCustomerIdAndCurrency(customerId, orderRequest.supportedCurrency());

		if (customerTradingAccount.isEmpty())
		{
			CustomerTradingAccountSaveRequest customerTradingAccountSaveRequest = new CustomerTradingAccountSaveRequest(customerId, orderRequest.supportedCurrency(), orderRequest.orderSize());
			saveCustomerTradingAccount(customerTradingAccountSaveRequest);
			return;
		}

		updateCurrencyAmount(customerId, orderRequest.supportedCurrency(), orderRequest.orderSize().add(customerTradingAccount.get().currencyAmount()));
	}

	public void validateCustomerSellOrderRequest(Optional<CustomerTradingAccount> customerTradingAccount, long customerId, OrderRequest orderRequest, long orderId)
	{
		if (customerTradingAccount.isEmpty())
		{
			logger.warn(String.format("Failed to execute order {%d}, reason: customer {%d} has no such investment", orderId, customerId));
			throw new NoSuchElementException("Customer trading account not found");
		}

		if (customerTradingAccount.get().currencyAmount().compareTo(orderRequest.orderSize()) < 0)
		{
			logger.warn(String.format("Failed to execute order {%d}, reason: customer {%d} has not enough funds", orderId, customerId));
			throw new NotEnoughFundsException("Not enough balance to execute order");
		}
	}
}
