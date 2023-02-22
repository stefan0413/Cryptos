package com.example.payment.repository;

import com.example.payment.model.deposit.Deposit;
import com.example.payment.model.deposit.DepositRequest;
import com.example.payment.repository.row_mappers.DepositRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class DepositRepository
{

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final DepositRowMapper depositRowmapper;

	public DepositRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DepositRowMapper depositRowmapper)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.depositRowmapper = depositRowmapper;
	}

	public void save(DepositRequest depositRequest)
	{
		String sql = """
							INSERT INTO deposit (customer_id, stripe_payment_method_id, stripe_payment_intent_id, amount, currency_code, status_id, created_at)
							VALUES(:customer_id, :stripe_payment_method_id, :stripe_payment_intent_id, :amount, :currency_code,
							 (SELECT id FROM n_deposit_statuses WHERE name = :name), :created_at)
				""";

		Map<String, Object> params = Map.of(
				"customer_id", depositRequest.customerId(),
				"stripe_payment_method_id", depositRequest.stripePaymentMethodId(),
				"stripe_payment_intent_id", depositRequest.stripePaymentIntentId(),
				"amount", depositRequest.amount(),
				"currency_code", depositRequest.currencyCode(),
				"name", depositRequest.status().name(),
				"created_at", depositRequest.created_at());

		namedParameterJdbcTemplate.update(sql, params);
	}

	public void updateStatus(String paymentIntentId, String status)
	{
		final String sql = "UPDATE deposit SET status_id = (SELECT id FROM n_deposit_statuses WHERE name = :name) WHERE stripe_payment_intent_id = :stripe_payment_intent_id";

		Map<String, Object> params = Map.of("stripe_payment_intent_id", paymentIntentId,
											"name", status);

		namedParameterJdbcTemplate.update(sql, params);
	}

	public void updateExecutionTimestamp( String paymentIntentId, LocalDateTime executedAt)
	{
		final String sql = "UPDATE deposit SET executed_at = :executed_at WHERE stripe_payment_intent_id = :stripe_payment_intent_id";

		Map<String, Object> params = Map.of("stripe_payment_intent_id", paymentIntentId,
											"executed_at", executedAt);

		namedParameterJdbcTemplate.update(sql, params);
	}

	public List<Deposit> getAllDepositsForCustomer(long customerId)
	{
		String sql = "SELECT deposit.id as id, customer_id, stripe_payment_method_id, stripe_payment_intent_id, amount, currency_code, n_deposit_statuses.name as status, created_at, executed_at FROM deposit " +
					 "LEFT JOIN n_deposit_statuses ON n_deposit_statuses.id = deposit.status_id " +
					 "WHERE customer_id = :customer_id";

		return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId), depositRowmapper);
	}
}
