package com.example.payment.repository;

import com.example.payment.model.withdrawal.Withdrawal;
import com.example.payment.model.withdrawal.WithdrawalRequest;
import com.example.payment.model.withdrawal.WithdrawalStatus;
import com.example.payment.repository.row_mappers.WithdrawalRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WithdrawalRepository
{

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final WithdrawalRowMapper withdrawalRowMapper;

	public WithdrawalRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, WithdrawalRowMapper withdrawalRowMapper)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.withdrawalRowMapper = withdrawalRowMapper;
	}

	public void saveWithdrawalRequest(WithdrawalRequest withdrawalRequest)
	{
		String sql = """
							INSERT INTO withdrawal (customer_id, iban, amount, status_id, created_at)
							VALUES(:customer_id, :iban, :amount, (SELECT id FROM n_withdrawal_statuses WHERE name = :status_name), :created_at)
				""";

		Map<String, Object> params = Map.of(
				"customer_id", withdrawalRequest.customerId(),
				"iban", withdrawalRequest.iban(),
				"amount", withdrawalRequest.amount(),
				"status_name", withdrawalRequest.status().name(),
				"created_at", withdrawalRequest.createdAt());

		namedParameterJdbcTemplate.update(sql, params);
	}

	public void updateStatus(long id, WithdrawalStatus withdrawalStatus)
	{
		final String sql = "UPDATE withdrawal SET status_id = (SELECT id FROM n_withdrawal_statuses WHERE name = :status_name) WHERE id = :id";

		Map<String, Object> params = Map.of("id", id,
											"status_name", withdrawalStatus.name());

		namedParameterJdbcTemplate.update(sql, params);
	}

	public List<Withdrawal> getWithdrawalsForCustomer(long customerId)
	{
			String sql = """
						SELECT withdrawal.id as id, customer_id, iban, amount, n_withdrawal_statuses.name as status, created_at FROM withdrawal
						LEFT JOIN  n_withdrawal_statuses ON n_withdrawal_statuses.id = withdrawal.status_id
						WHERE customer_id = :customer_id""";

			return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId), withdrawalRowMapper);
	}

	public List<Withdrawal> getPendingWithdrawalsForCustomer(long customerId)
	{
		String sql = """
				SELECT withdrawal.id as id, customer_id, iban, amount, n_withdrawal_statuses.name as status, created_at FROM withdrawal
				LEFT JOIN  n_withdrawal_statuses ON n_withdrawal_statuses.id = withdrawal.status_id
				WHERE customer_id = :customer_id 
				AND status_id = (SELECT id FROM n_withdrawal_statuses WHERE name = :status_name) """;

		return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId,
															"status_name", WithdrawalStatus.PENDING.name()), withdrawalRowMapper);
	}
}
