package com.example.payment.repository;

import com.example.payment.exception.NoSuchCustomerStripeAccount;
import com.example.payment.exception.PaymentsException;
import com.example.payment.model.customer_stripe_account.CustomerStripeAccount;
import com.example.payment.model.customer_stripe_account.CustomerStripeAccountRequest;
import com.example.payment.model.customer_stripe_account.CustomerWithFreeBalanceAndCurrencyResponse;
import com.example.payment.repository.row_mappers.CustomerFreeBalanceWithCurrencyRowMapper;
import com.example.payment.repository.row_mappers.CustomerStripeAccountRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

@Repository
public class CustomerStripeAccountRepository
{

	private static final String BASE_SELECT_QUERY = "SELECT * FROM customer_stripe_account ";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final CustomerStripeAccountRowMapper customerStripeAccountRowMapper;

	public CustomerStripeAccountRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
										   CustomerStripeAccountRowMapper customerStripeAccountRowMapper)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.customerStripeAccountRowMapper = customerStripeAccountRowMapper;
	}

	public void save(CustomerStripeAccountRequest customerStripeAccountRequest)
	{
		String sql = """
						    INSERT INTO customer_stripe_account (customer_id, currency_code, free_balance, invested_balance, email, names)
						    VALUES(:customer_id, :currency_code, :free_balance, :invested_balance, :email, :names)
				""";
		Map<String, Object> params = Map.of("customer_id", customerStripeAccountRequest.customerId(),
											"currency_code", customerStripeAccountRequest.currencyCode(),
											"free_balance", customerStripeAccountRequest.freeBalance(),
											"invested_balance", customerStripeAccountRequest.investedBalance(),
											"email", customerStripeAccountRequest.email(),
											"names", customerStripeAccountRequest.names());
		namedParameterJdbcTemplate.update(sql, params);
	}

	public void updateCustomerBalance(Long customerId, BigDecimal freeBalance)
	{
		String sql = """
				UPDATE customer_stripe_account SET
				free_balance = :free_balance
				WHERE customer_id = :customer_id
				""";
		Map<String, Object> params = Map.of("customer_id", customerId,
											"free_balance", freeBalance);

		namedParameterJdbcTemplate.update(sql, params);
	}

	public CustomerStripeAccount getCustomerStripeAccountByCustomerId(long customerId)
	{
		String sql = BASE_SELECT_QUERY + "WHERE customer_id=:customer_id";
		try
		{
			return namedParameterJdbcTemplate.queryForObject(sql, Map.of("customer_id", customerId), customerStripeAccountRowMapper);
		}
		catch (EmptyResultDataAccessException ex)
		{
			throw new NoSuchCustomerStripeAccount(String.format("No such customerStripeAccount, customeId: %d", customerId));
		}
	}

	public CustomerWithFreeBalanceAndCurrencyResponse getCustomerFreeBalance(long customerId)
	{
		String sql = "SELECT customer_id, free_balance, currency_code FROM customer_stripe_account WHERE customer_id=:customer_id";

		try
		{
			return namedParameterJdbcTemplate.queryForObject(sql, Map.of("customer_id", customerId), new CustomerFreeBalanceWithCurrencyRowMapper());
		}
		catch (RuntimeException ex)
		{
			System.out.println("Exception: " + String.format("No such customerStripeAccount, customeId: %d", customerId));
			throw new NoSuchCustomerStripeAccount(String.format("No such customerStripeAccount, customeId: %d", customerId));
		}
	}
}
